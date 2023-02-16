package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.SetmealDishMapper;
import com.alice.zhaizhai.pojo.SetmealDish;
import com.alice.zhaizhai.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月01日 10:48
 */
@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public void save(SetmealDish setmealDish) {
        setmealDishMapper.insert(setmealDish);
    }

    @Override
    public void delete(Long setmealId) {
        setmealDishMapper.deleteBySetmealId(setmealId);
    }

    @Override
    public Long countByDishId(Long dishId) {
        return setmealDishMapper.countByDishId(dishId);
    }
}
