package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月07日 21:35
 */
@Repository
public interface OrderDetailMapper {
    void insert(OrderDetail orderDetail);

    List<OrderDetail> selectListByOrderId(Long orderId);
}
