package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.SetmealDish;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月30日 11:34
 */
@Repository
public interface SetmealDishMapper {
    void insert(SetmealDish setmealDish);

    List<SetmealDish> selectListBySetmealId(Long setmealId);

    void deleteBySetmealId(Long setmealId);

    Long countByDishId(Long dishId);
}
