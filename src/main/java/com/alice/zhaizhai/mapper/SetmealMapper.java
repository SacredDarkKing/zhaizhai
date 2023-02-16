package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.dto.SetmealDto;
import com.alice.zhaizhai.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:38
 */
@Repository
public interface SetmealMapper {
    List<Setmeal> selectListByCategoryId(Long categoryId);

    List<SetmealDto> selectSetmealDtoListLikeName(String name);

    void insert(Setmeal setmeal);

    SetmealDto selectSetmealDtoById(Long id);

    void update(Setmeal setmeal);

    Setmeal selectById(Long id);

    List<Setmeal> selectListByCondition(Setmeal setmeal);

    List<Map> selectDishListById(Long id);
}
