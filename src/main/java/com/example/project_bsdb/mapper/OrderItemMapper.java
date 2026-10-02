package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Response.OrderItemResponse;
import com.example.project_bsdb.entities.OrderItemEntity;
import com.example.project_bsdb.entities.ProductEntity;

import java.math.BigDecimal;

public class OrderItemMapper {

    public static OrderItemResponse toResponseDto(OrderItemEntity orderItem) {

        if (orderItem == null) {
            return null;
        }

        OrderItemResponse response = new OrderItemResponse();

        response.setId(orderItem.getId());
        response.setQuantity(orderItem.getQuantity());
        response.setPrice(orderItem.getPrice());
        if (orderItem.getPrice() != null
                && orderItem.getQuantity() != null) {

            BigDecimal subtotal = orderItem.getPrice()
                    .multiply(
                            BigDecimal.valueOf(orderItem.getQuantity())
                    );

            response.setSubtotal(subtotal);
        }
        ProductEntity product = orderItem.getProduct();

        if (product != null) {
            response.setProductId(product.getId());
            response.setProductName(product.getName());
        }

        return response;
    }
}
