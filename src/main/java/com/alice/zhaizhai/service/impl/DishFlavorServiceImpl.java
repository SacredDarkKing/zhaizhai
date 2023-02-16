package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.DishFlavorMapper;
import com.alice.zhaizhai.pojo.DishFlavor;
import com.alice.zhaizhai.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月26日 15:42
 */
@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public void save(DishFlavor dishFlavor) {
        dishFlavorMapper.insert(dishFlavor);
    }

    @Override
    public void delete(Long dishId) {
        dishFlavorMapper.deleteByDishId(dishId);
    }


}
