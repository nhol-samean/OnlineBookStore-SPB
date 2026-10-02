package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.CartItemRequest;
import com.example.project_bsdb.dto.Response.CartResponse;

public interface CartService {

    CartResponse getCart(String username);

    CartResponse addToCart(String username, CartItemRequest request);

    CartResponse updateCartItem(String username, Long cartItemId, CartItemRequest request);

    void deleteCartItem(String username, Long cartItemId);
}
