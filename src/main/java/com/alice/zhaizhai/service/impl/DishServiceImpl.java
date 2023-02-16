package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.common.CustomException;
import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.dto.DishDto;
import com.alice.zhaizhai.mapper.DishMapper;
import com.alice.zhaizhai.pojo.Dish;
import com.alice.zhaizhai.pojo.DishFlavor;
import com.alice.zhaizhai.service.DishFlavorService;
import com.alice.zhaizhai.service.DishService;
import com.alice.zhaizhai.service.SetmealDishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:47
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private SetmealDishService setmealDishService;


    @Override
    public List<DishDto> getList(Dish dish) {
        return dishMapper.selectDishDtoListByCondition(dish);
    }

    @Override
    public List<DishDto> getList(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        return dishMapper.selectDishDtoListByCondition(dish);
    }

    @Override
    public MyPage getPage(int page, int pageSize, String name) {
        Page pageInfo = PageHelper.startPage(page, pageSize);
        dishMapper.selectDishDtoListLikeName(name);
        return new MyPage(pageInfo);
    }

    @Override
    public void saveDish(Dish dish) {
        dishMapper.insert(dish);
    }

    @Override
    @Transactional
    public void saveWithFlavors(DishDto dishDto) {
        this.saveDish(dishDto);
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(dishId);
            dishFlavorService.save(dishFlavor);
        }
    }

    @Override
    public DishDto getDishDto(Long id) {
        return dishMapper.selectDishDtoById(id);
    }

    @Override
    public void updateDish(Dish dish) {
        dishMapper.update(dish);
    }

    @Override
    @Transactional
    public void updateWithFlavors(DishDto dishDto) {
        this.updateDish(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long dishId = dishDto.getId();
        dishFlavorService.delete(dishId);//菜品的口味修改是先删除，后添加
        //因为菜品的口味有多条，可能修改的时候变多或者变少，无法直接修改

        for (DishFlavor dishFlavor : flavors) {
            //虽然传过来的口味有设置dishId，但是如果有新增的口味，就没有了，所以还是需要自己设置一遍
            dishFlavor.setDishId(dishId);
            //并且这里新增操作的Mapper的xml文件中，不要插入id字段，因为需要数据库的自增或者是程序的雪花算法
            dishFlavorService.save(dishFlavor);
        }
    }

    @Override
    @Transactional
    public void updateStatusBatch(Integer status, List<Long> ids) {
        Dish dish = new Dish();
        dish.setStatus(status);

        for (Long id : ids) {
            dish.setId(id);
            dishMapper.update(dish);
        }
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //删除是逻辑删除，就是将isdeleted字段设置为1
        Dish dish = new Dish();
        dish.setIsDeleted(1);

        for(Long id : ids) {
            //1.先检查这个dish是否为停售状态
            Dish dishById = dishMapper.selectById(id);
            String dishName = dishById.getName();
            if (dishById.getStatus() == 1)
                throw new CustomException(dishName + "为在售状态，请先停售");

            //2. 在删除前，先检查是否被其他套餐引用
            Long n = setmealDishService.countByDishId(id);
            if (n > 0)
                throw new CustomException("删除失败，有套餐关联了菜品：" + dishName);
            dish.setId(id);
            dishMapper.update(dish);
        }
    }

}
