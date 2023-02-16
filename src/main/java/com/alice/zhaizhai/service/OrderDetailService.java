package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.OrderDetail;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月07日 21:36
 */
public interface OrderDetailService {
    void save(OrderDetail orderDetail);

    List<OrderDetail> getListByOrderId(Long orderId);
}
