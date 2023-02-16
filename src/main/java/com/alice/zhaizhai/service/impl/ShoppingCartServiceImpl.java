package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.ShoppingCartMapper;
import com.alice.zhaizhai.pojo.ShoppingCart;
import com.alice.zhaizhai.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 16:48
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public List<ShoppingCart> getListByUserId(Long userId) {
        return shoppingCartMapper.selectListByUserId(userId);
    }

    @Override
    public ShoppingCart getOne(ShoppingCart shoppingCart) {
        //判断依据是菜品id 以及口味
        return shoppingCartMapper.selectOneByCondition(shoppingCart);
    }

    @Override
    public Integer getBuyCount(ShoppingCart shoppingCart) {
        //获取一个菜品或者套餐的购买数量，
        //特殊情况：菜品有多种口味，需要将这个菜品的每个口味的数量进行累加
        return shoppingCartMapper.selectNumber(shoppingCart);

    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartMapper.update(shoppingCart);
    }

    @Override
    @Transactional
    public void add(ShoppingCart shoppingCart) {
        //如果该购物车项存在，则number+1
        ShoppingCart exist = getOne(shoppingCart);
        if (exist == null) {//不存在的情况
            //新建一个购物车项
            shoppingCartMapper.insert(shoppingCart);
            return;
        }

        int number = exist.getNumber() + 1;
        shoppingCart.setId(exist.getId());//设置id，用于更新
        shoppingCart.setNumber(number);//设置数量+1
        shoppingCartMapper.update(shoppingCart);
        return;
    }

    @Override
    public void deleteById(Long id) {
        shoppingCartMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void sub(ShoppingCart shoppingCart) {
        //先根据传进来的用户id以及购物车菜品id查询
        //如果存在，并且数量等于1，则执行删除操作

        ShoppingCart exist = getOne(shoppingCart);
        if (exist == null) //不存在的情况
            return;

        int number = exist.getNumber();
        if (number == 1) {//数量等于1的情况执行删除
            shoppingCartMapper.deleteById(exist.getId());
            return;
        }

        //存在且数量大于1，更新number为number-1
        shoppingCart.setId(exist.getId());
        shoppingCart.setNumber(number - 1);
        shoppingCartMapper.update(shoppingCart);

    }

    @Override
    public void deleteByUserId(Long userId) {
        shoppingCartMapper.deleteByUserId(userId);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        shoppingCartMapper.insert(shoppingCart);
    }
}
