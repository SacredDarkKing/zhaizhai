package com.alice.zhaizhai.service;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.dto.SetmealDto;
import com.alice.zhaizhai.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:47
 */
public interface SetmealService {
    List<Setmeal> getList(Long categoryId);

    MyPage getPage(int page, int pageSize, String name);

    void saveSetmeal(Setmeal setmeal);

    void saveWithDishes(SetmealDto setmealDto);

    SetmealDto getSetmealDto(Long id);

    void updateWihtDishes(SetmealDto setmealDto);

    void updateStatusBatch(Integer status, List<Long> ids, Long empId);

    void deleteBatch(List<Long> ids);

    List<Setmeal> getList(Setmeal setmeal);

    List<Map> getDishListById(Long id);
}
