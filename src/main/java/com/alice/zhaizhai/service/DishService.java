package com.alice.zhaizhai.service;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.dto.DishDto;
import com.alice.zhaizhai.pojo.Dish;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:47
 */
public interface DishService {
    List<DishDto> getList(Dish dish);

    List<DishDto> getList(Long categoryId);

    MyPage getPage(int page, int pageSize, String name);

    void saveDish(Dish dish);

    void saveWithFlavors(DishDto dishDto);

    DishDto getDishDto(Long id);

    void updateDish(Dish dish);

    void updateWithFlavors(DishDto dishDto);

    void updateStatusBatch(Integer status, List<Long> ids, Long empId);

    void deleteBatch(List<Long> ids);
}
