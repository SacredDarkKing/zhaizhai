package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.common.CustomException;
import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.mapper.CategoryMapper;
import com.alice.zhaizhai.pojo.Category;
import com.alice.zhaizhai.service.CategoryService;
import com.alice.zhaizhai.service.DishService;
import com.alice.zhaizhai.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月20日 15:12
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void save(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public MyPage getPage(Integer page, Integer pageSize) {
        Page<Object> pageInfo = PageHelper.startPage(page, pageSize);
        categoryMapper.selectAll();
        return new MyPage(pageInfo);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {

        if (dishService.getList(id).size() > 0)
            throw new CustomException("删除失败，该分类已关联菜品");

        if (setmealService.getList(id).size() > 0)
            throw new CustomException("删除失败，该分类已关联套餐");

        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> getListByCondition(Category category) {
        return categoryMapper.selectListByCondition(category);
    }


}
