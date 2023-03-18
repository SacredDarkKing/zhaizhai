package com.alice.zhaizhai.mapper;


import com.alice.zhaizhai.dto.OrderDto;
import com.alice.zhaizhai.pojo.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月30日 16:35
 */
@Repository
public interface OrderMapper {

    List<OrderDto> selectListByCondition(Long userId, String number, LocalDateTime beginTime, LocalDateTime endTime);

    void insert(Order order);

    void update(Order order);

    Order selectLatestOrderByUserId(Long userId);
}
