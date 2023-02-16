package com.alice.zhaizhai.service;

import com.alice.zhaizhai.common.MyPage;
import com.alice.zhaizhai.pojo.Order;

import java.time.LocalDateTime;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月30日 16:37
 */
public interface OrderService {
    MyPage getPage(Long userId, Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime);

    void submit(Order order);

    void update(Order order);

    void again(Order order);
}
