package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.dto.SetmealDto;
import com.alice.zhaizhai.pojo.Setmeal;
import com.alice.zhaizhai.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月29日 18:30
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<MyPage> page(Integer page, Integer pageSize, String name) {
        MyPage myPage = setmealService.getPage(page, pageSize, name);
        return R.success(myPage);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable("id") Long id) {
        SetmealDto setmealDto = setmealService.getSetmealDto(id);
        return R.success(setmealDto);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto, HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("employee");
        setmealDto.createOrUpdate(id);
        setmealService.saveWithDishes(setmealDto);
        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto, HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("employee");
        setmealDto.createOrUpdate(id);
        setmealService.updateWihtDishes(setmealDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{st}")
    public R<String> updateStatusBatch(@PathVariable("st") Integer status, @RequestParam List<Long> ids, HttpSession session) {
        Long empId = (Long)session.getAttribute("employee");
        setmealService.updateStatusBatch(status, ids, empId);
        return R.success("状态修改成功");
    }

    @DeleteMapping
    public R<String> deleteBatch(@RequestParam List<Long> ids) {
        setmealService.deleteBatch(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        List<Setmeal> setmeals = setmealService.getList(setmeal);
        return R.success(setmeals);
    }

    @GetMapping("/dish/{id}")
    public R<List<Map>> dish(@PathVariable("id") Long id) {
        List<Map> dishes = setmealService.getDishListById(id);
        return R.success(dishes);
    }
}
