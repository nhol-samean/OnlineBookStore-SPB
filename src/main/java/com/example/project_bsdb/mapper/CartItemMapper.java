package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.CartItemRequest;
import com.example.project_bsdb.dto.Response.CartItemResponse;
import com.example.project_bsdb.entities.CartItemEntity;

import java.math.BigDecimal;
public class CartItemMapper {

    public static CartItemEntity toEntity(CartItemRequest request) {

        if (request == null) {
            return null;
        }
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setQuantity(request.getQuantity());

        return cartItem;
    }
    public static CartItemResponse toResponseDto(CartItemEntity cartItem) {
        if (cartItem == null) {
            return null;
        }
        BigDecimal price = cartItem.getProduct().getPrice();
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        CartItemResponse response = new CartItemResponse();

        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(price);
        response.setSubtotal(subtotal);
        return response;
    }
}
