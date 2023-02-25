package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.common.CustomException;
import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.dto.SetmealDto;
import com.alice.zhaizhai.mapper.SetmealMapper;
import com.alice.zhaizhai.pojo.Setmeal;
import com.alice.zhaizhai.pojo.SetmealDish;
import com.alice.zhaizhai.service.SetmealDishService;
import com.alice.zhaizhai.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.TreeUI;
import java.util.List;
import java.util.Map;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月23日 16:48
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Cacheable(value = "setmeal", key = "#categoryId", unless = "#result == null")
    public List<Setmeal> getList(Long categoryId) {
        return setmealMapper.selectListByCategoryId(categoryId);
    }

    @Override
    public MyPage getPage(int page, int pageSize, String name) {
        Page<SetmealDto> pageInfo = PageHelper.startPage(page, pageSize);
        setmealMapper.selectSetmealDtoListLikeName(name);
        return new MyPage(pageInfo);
    }

    @Override
    public void saveSetmeal(Setmeal setmeal) {
        setmealMapper.insert(setmeal);
    }

    @Override
    @Transactional
    @CacheEvict(value = "setmeal", key = "#setmealDto.categoryId")
    public void saveWithDishes(SetmealDto setmealDto) {
        this.saveSetmeal(setmealDto);
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
            setmealDishService.save(setmealDish);
        }

    }

    @Override
    public SetmealDto getSetmealDto(Long id) {
        return setmealMapper.selectSetmealDtoById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "setmeal", key = "#setmealDto.categoryId")
    public void updateWihtDishes(SetmealDto setmealDto) {
        setmealMapper.update(setmealDto);
        Long setmealId = setmealDto.getId();
        setmealDishService.delete(setmealId);//先删除套餐的菜品，再添加，这样就完成了更新操作

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
            setmealDishService.save(setmealDish);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "setmeal", allEntries = true)
    public void updateStatusBatch(Integer status, List<Long> ids) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);

        for (Long id : ids) {
            setmeal.setId(id);
            setmealMapper.update(setmeal);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "setmeal", allEntries = true)
    public void deleteBatch(List<Long> ids) {
        //逻辑删除
        //需要套餐为停售状态
        Setmeal setmeal = new Setmeal();
        setmeal.setIsDeleted(1);

        for (Long id : ids) {
            Setmeal setmealById = setmealMapper.selectById(id);
            if (setmealById.getStatus() == 1)
                throw new CustomException(setmealById.getName() + "为在售状态，请改为停售");

            setmeal.setId(id);
            setmealMapper.update(setmeal);
        }
    }

    @Override
    @Cacheable(value = "setmeal", key = "#setmeal.categoryId", unless = "#result == null")
    public List<Setmeal> getList(Setmeal setmeal) {
        return setmealMapper.selectListByCondition(setmeal);
    }

    @Override
    public List<Map> getDishListById(Long id) {
        return setmealMapper.selectDishListById(id);
    }


}
