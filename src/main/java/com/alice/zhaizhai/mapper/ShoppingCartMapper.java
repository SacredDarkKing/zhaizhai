package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 16:48
 */
@Repository
public interface ShoppingCartMapper {
    List<ShoppingCart> selectListByUserId(Long userId);

    void insert(ShoppingCart shoppingCart);

    //根据用户id/菜品id和口味/套餐id查询
    ShoppingCart selectOneByCondition(ShoppingCart shoppingCart);

    //获取菜品/套餐的数量
    Integer selectNumber(ShoppingCart shoppingCart);

    //更新只会更新数量 和 创建时间
    void update(ShoppingCart shoppingCart);

    void deleteById(Long id);

    void deleteByUserId(Long userId);



}
