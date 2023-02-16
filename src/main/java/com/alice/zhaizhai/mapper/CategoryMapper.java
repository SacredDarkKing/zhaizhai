package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    void insert(Category category);

    List<Category> selectAll();

    void update(Category category);

    void deleteById(Long id);

    List<Category> selectListByCondition(Category category);
}
