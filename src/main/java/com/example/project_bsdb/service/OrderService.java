package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.OrderRequest;
import com.example.project_bsdb.dto.Response.OrderResponse;
import com.example.project_bsdb.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request, String username);

    List<OrderResponse> getOrderByUsername(String username);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUserId(Long userId);

    OrderResponse updateOrderStatus(Long id, OrderStatus status);

    void deleteOrder(Long id);
}
