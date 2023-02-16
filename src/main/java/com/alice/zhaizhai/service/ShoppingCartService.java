package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.ShoppingCart;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 16:48
 */
public interface ShoppingCartService {
    List<ShoppingCart> getListByUserId(Long userId);

    ShoppingCart getOne(ShoppingCart shoppingCart);

    Integer getBuyCount(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void add(ShoppingCart shoppingCart);

    void deleteById(Long id);

    void sub(ShoppingCart shoppingCart);

    void deleteByUserId(Long userId);

    void save(ShoppingCart shoppingCart);
}
