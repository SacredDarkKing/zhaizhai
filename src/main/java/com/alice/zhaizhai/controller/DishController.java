package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.dto.DishDto;
import com.alice.zhaizhai.pojo.Dish;
import com.alice.zhaizhai.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月24日 17:03
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<MyPage> page(Integer page, Integer pageSize, String name) {
        MyPage dishPage = dishService.getPage(page, pageSize, name);
        log.info(dishPage.toString());
        return R.success(dishPage);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto, HttpServletRequest request) {
        log.info(dishDto.toString());
        Long id = (Long) request.getSession().getAttribute("employee");
        dishDto.createOrUpdate(id);
        dishService.saveWithFlavors(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable("id") Long id) {
        log.info("id={}", id);
        DishDto dishDto = dishService.getDishDto(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto, HttpServletRequest request) {
        log.error("dishDto:{}", dishDto.toString());
        dishDto.createOrUpdate((Long) request.getSession().getAttribute("employee"));
        dishService.updateWithFlavors(dishDto);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishList = dishService.getList(dish);
        return R.success(dishList);
    }

    @PostMapping("/status/{st}")
    public R<String> updateStatusBatch(@PathVariable("st") Integer status,@RequestParam List<Long> ids) {
        dishService.updateStatusBatch(status, ids);
        return R.success("状态修改成功");
    }


    @DeleteMapping
    public R<String> deleteBatch(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        return R.success("删除成功");
    }

}
