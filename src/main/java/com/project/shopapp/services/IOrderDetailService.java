package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail updateOrderDetail(OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(Long id);
    List<OrderDetail> getOrderDetails(Long orderId) throws DataNotFoundException;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
}
