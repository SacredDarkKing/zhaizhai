package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.common.CustomException;
import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.mapper.OrderMapper;
import com.alice.zhaizhai.pojo.*;
import com.alice.zhaizhai.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月30日 16:37
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public MyPage getPage(Long userId, Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime) {
        Page<Object> pageInfo = PageHelper.startPage(page, pageSize);
        orderMapper.selectListByCondition(userId,number, beginTime, endTime);
        return new MyPage(pageInfo);
    }

    @Override
    @Transactional
    public void submit(Order order) {
        //1. 补全订单数据的信息
        //设置订单的订单号
        order.setNumber(UUID.randomUUID().toString());

        //设置订单的状态
        order.setStatus(2);//待派送

        //设置订单的创建时间、付款时间
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());

        //设置订单的实收金额
        //首先要查询购物车数据
        List<ShoppingCart> cartList = shoppingCartService.getListByUserId(order.getUserId());
        //判断一下购物车是否为空
        if (cartList == null || cartList.size() == 0) //如果是空的，则报错
            throw new CustomException("购物车为空，无法下单");

        AtomicInteger amount = new AtomicInteger(0);
        for (ShoppingCart cart : cartList) {
            amount.addAndGet(cart.getAmount().multiply(new BigDecimal(cart.getNumber())).intValue());
        }
        order.setAmount(new BigDecimal(amount.get()));

        //设置用户名
        User user = userService.getById(order.getUserId());
        String userName = user.getName();
        order.setUserName(userName);

        //设置收货地址相关数据：
        AddressBook addressBook = addressBookService.getById(order.getAddressBookId());
        if (addressBook == null)//防止用户空地址
            throw new CustomException("请填写地址");

        // 收获手机号
        order.setPhone(addressBook.getPhone());
        // 收获地址
        order.setAddress(addressBook.getDetail());
        // 收获人姓名
        order.setConsignee(addressBook.getConsignee());


        //2. 新建订单数据
        orderMapper.insert(order);

        //3.设置订单明细数据
        //获取订单的id
        Long orderId = order.getId();
        //订单明细取决于购物车的数据
        //每一条购物车数据就是一条订单明细数据
        for (ShoppingCart cart : cartList) {
            //设置订单明细的数据
            OrderDetail orderDetail = new OrderDetail();
            //设置菜品/套餐名称
            orderDetail.setName(cart.getName());
            //设置订单id
            orderDetail.setOrderId(orderId);
            //设置菜品id
            orderDetail.setDishId(cart.getDishId());
            //设置套餐id
            orderDetail.setSetmealId(cart.getSetmealId());
            //设置菜品口味
            orderDetail.setDishFlavor(cart.getDishFlavor());
            //设置数量
            orderDetail.setNumber(cart.getNumber());
            //设置单价
            orderDetail.setAmount(cart.getAmount());
            //设置
            orderDetail.setImage(cart.getImage());
            //4. 向数据库插入
            orderDetailService.save(orderDetail);
        }

        //5. 清空用户购物车
        shoppingCartService.deleteByUserId(order.getUserId());
    }

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    @Transactional
    public void again(Order order) {
        //再来一单的功能是将此此订单的菜品重新加入购物车
        //先清空购物车
        shoppingCartService.deleteByUserId(order.getUserId());

        List<OrderDetail> orderDetails = orderDetailService.getListByOrderId(order.getId());

        for (OrderDetail orderDetail : orderDetails) {
            ShoppingCart shoppingCart = new ShoppingCart();

            //设置购物车的用户id
            shoppingCart.setUserId(order.getUserId());

            //设置名字
            shoppingCart.setName(orderDetail.getName());

            //设置金额
            shoppingCart.setAmount(orderDetail.getAmount());

            //设置数量
            shoppingCart.setNumber(orderDetail.getNumber());

            //设置购物车的菜品/套餐id
            if (orderDetail.getDishId() != null) {
                shoppingCart.setDishId(orderDetail.getDishId());
                if (orderDetail.getDishFlavor() != null) {
                    shoppingCart.setDishFlavor(orderDetail.getDishFlavor());
                }
            } else if (orderDetail.getSetmealId() != null) {
                shoppingCart.setSetmealId(orderDetail.getSetmealId());
            }

            shoppingCart.setImage(orderDetail.getImage());

            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartService.save(shoppingCart);

        }

    }
}
