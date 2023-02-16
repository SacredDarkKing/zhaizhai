package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.Category;
import com.alice.zhaizhai.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月20日 15:12
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<MyPage> page(Integer page, Integer pageSize) {
        MyPage myPage = categoryService.getPage(page, pageSize);
        return R.success(myPage);
    }

    @PostMapping
    public R<String> save(@RequestBody Category category, HttpServletRequest request) {
        category.createOrUpdate((Long) request.getSession().getAttribute("employee"));
        categoryService.save(category);
        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category, HttpServletRequest request) {
        category.createOrUpdate((Long) request.getSession().getAttribute("employee"));
        categoryService.update(category);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        categoryService.delete(id);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        return R.success(categoryService.getListByCondition(category));
    }
}
