package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.DishFlavor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月26日 15:39
 */
@Repository
public interface DishFlavorMapper {

    void insert(DishFlavor dishFlavor);

    List<DishFlavor> selectListByDishId(Long dishId);

    void deleteByDishId(Long dishId);
}
