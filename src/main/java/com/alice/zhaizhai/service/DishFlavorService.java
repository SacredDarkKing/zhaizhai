package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.DishFlavor;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月26日 15:42
 */
public interface DishFlavorService {
    void save(DishFlavor dishFlavor);

    void delete(Long dishId);

}
