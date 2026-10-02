package com.example.project_bsdb.mapper;
import com.example.project_bsdb.dto.Request.OrderRequest;
import com.example.project_bsdb.dto.Response.OrderItemResponse;
import com.example.project_bsdb.dto.Response.OrderResponse;
import com.example.project_bsdb.entities.OrderEntity;
import com.example.project_bsdb.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static OrderEntity toEntity(OrderRequest request) {

        if (request == null) {
            return null;
        }
        OrderEntity order = new OrderEntity();

        order.setOrder_date(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotal_amount(BigDecimal.ZERO);

        return order;
    }
    public static OrderResponse toResponseDto(OrderEntity order) {

        if (order == null) {
            return null;
        }
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setOrderDate(order.getOrder_date());
        response.setTotalAmount(order.getTotal_amount());
        response.setStatus(order.getStatus());
        if (order.getUser() != null) {
            response.setUserId(order.getUser().getId());
            response.setUsername(order.getUser().getUsername());
        }
        if (order.getAddress() != null) {
            response.setAddressId(order.getAddress().getId());
        }
        if (order.getOrderItems() != null) {

            List<OrderItemResponse> orderItems = order.getOrderItems()
                    .stream()
                    .map(OrderItemMapper::toResponseDto)
                    .toList();

            response.setOrderItems(orderItems);

        } else {
            response.setOrderItems(new ArrayList<>());
        }

        response.setCreated_at(order.getCreated_at());
        response.setUpdated_at(order.getUpdated_at());

        return response;
    }
}
