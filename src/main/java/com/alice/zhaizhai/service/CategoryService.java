package com.alice.zhaizhai.service;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.pojo.Category;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月20日 15:11
 */
public interface CategoryService {
    void save(Category category);

    MyPage getPage(Integer page, Integer pageSize);

    void update(Category category);

    void delete(Long id);

    List<Category> getListByCondition(Category category);
}
