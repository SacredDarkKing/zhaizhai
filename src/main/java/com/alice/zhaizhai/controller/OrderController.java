package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.CustomException;
import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.Order;
import com.alice.zhaizhai.service.OrderService;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月30日 16:37
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService ordersService;

    @GetMapping("/userPage")
    public R<MyPage> userPage(Integer page, Integer pageSize, HttpSession session) {
        //检查是否登录
        Object user = session.getAttribute("user");
        if (user == null)
            return R.error("NOTLOGIN");

        Long userId = (Long) user;

        MyPage myPage = ordersService.getPage(userId, page, pageSize, null, null, null);
        return R.success(myPage);
    }

    @GetMapping("/page")
    public R<MyPage> page(Integer page, Integer pageSize, String number, String beginTime, String endTime, HttpSession session) {
        //检查是否登录
        Object employee = session.getAttribute("employee");
        if (employee == null)
            return R.error("NOTLOGIN");

        LocalDateTime endTime_ = null;
        LocalDateTime beginTime_ = null;
        if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            beginTime_ = LocalDateTime.parse(beginTime, formatter);
            endTime_ = LocalDateTime.parse(endTime, formatter);
        }


        MyPage myPage = ordersService.getPage(null, page, pageSize, number, beginTime_, endTime_);
        return R.success(myPage);
    }

    @PostMapping("/submit")
    public R<Order> submit(@RequestBody Order order, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        //设置订单的用户id
        order.setUserId(userId);
        ordersService.submit(order);
        return R.success(order);
    }

    @PutMapping
    public R<String> update(@RequestBody Order order) {
        ordersService.update(order);
        return R.success("修改成功");
    }

    @PostMapping("/again")
    public R<String> again(@RequestBody Order order, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null)
            return R.error("NOTLOGIN");

        Long userId = (Long) user;
        order.setUserId(userId);
        ordersService.again(order);
        return R.success("操作成功");
    }
}
