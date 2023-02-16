package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.ShoppingCart;
import com.alice.zhaizhai.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月03日 16:45
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpSession session) {
        //获取当前用户的购物车
        Long userId = (Long) session.getAttribute("user");
        List<ShoppingCart> shoppingCarts = shoppingCartService.getListByUserId(userId);
        return R.success(shoppingCarts);
    }


    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");//获取用户id
        shoppingCart.setCreateTime(LocalDateTime.now());//获取时间
        shoppingCart.setUserId(userId);//设置用户id
        shoppingCart.setNumber(1);//需要设置number为1，方便添加
        shoppingCartService.add(shoppingCart);

        //返回前需要设置数量，前端需要该数据更新页面
        shoppingCart.setNumber(shoppingCartService.getBuyCount(shoppingCart));
        return R.success(shoppingCart);
    }


    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setUserId(userId);
        shoppingCart.setNumber(0);//需要设置number为0，方便返回数据
        shoppingCartService.sub(shoppingCart);

        //返回前需要设置数量，前端需要该数据更新页面
        shoppingCart.setNumber(shoppingCartService.getBuyCount(shoppingCart));
        return R.success(shoppingCart);
    }

    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        shoppingCartService.deleteByUserId(userId);
        return R.success("购物车已清空");
    }

}
