package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.dto.DishDto;
import com.alice.zhaizhai.pojo.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:39
 */
@Repository
public interface DishMapper {

    List<DishDto> selectDishDtoListByCondition(Dish dish);

    List<DishDto> selectDishDtoListLikeName(String name);

    void insert(Dish dish);

    DishDto selectDishDtoById(Long id);

    void update(Dish dish);

    Dish selectById(Long id);
}
