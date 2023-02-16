package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.SetmealDish;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月01日 10:48
 */
public interface SetmealDishService {
    void save(SetmealDish setmealDish);

    void delete(Long setmealId);

    Long countByDishId(Long dishId);
}
