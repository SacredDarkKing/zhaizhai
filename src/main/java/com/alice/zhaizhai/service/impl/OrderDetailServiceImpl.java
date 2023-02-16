package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.OrderDetailMapper;
import com.alice.zhaizhai.pojo.OrderDetail;
import com.alice.zhaizhai.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月07日 21:36
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailMapper.insert(orderDetail);
    }

    @Override
    public List<OrderDetail> getListByOrderId(Long orderId) {
        return orderDetailMapper.selectListByOrderId(orderId);
    }
}
