package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Response.CartItemResponse;
import com.example.project_bsdb.dto.Response.CartResponse;
import com.example.project_bsdb.entities.CartEntity;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartResponse toResponseDto(CartEntity cart) {
        if (cart == null) {
            return null; } List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(CartItemMapper::toResponseDto)
                .toList();
        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .username(cart.getUser() != null ? cart.getUser().getUsername() : null)
                .items(items)
                .totalAmount(totalAmount)
                .build();
    }
}
