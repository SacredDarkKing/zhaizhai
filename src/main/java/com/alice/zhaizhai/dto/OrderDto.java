package com.alice.zhaizhai.dto;

import com.alice.zhaizhai.pojo.OrderDetail;
import com.alice.zhaizhai.pojo.Order;
import lombok.Data;
import java.util.List;

@Data
public class OrderDto extends Order {

//    private String userName;
//
//    private String phone;
//
//    private String address;
//
//    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
