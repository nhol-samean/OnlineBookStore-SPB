package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.CartItemRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.CartResponse;
import com.example.project_bsdb.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "Cart Management APIs")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get current user's cart")
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(
            Authentication authentication) {

        String username = authentication.getName();

        CartResponse cart = cartService.getCart(username);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart retrieved successfully", cart
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add product to current user's cart")
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request) {

        String username = authentication.getName();

        CartResponse cart = cartService.addToCart(
                username,
                request
        );

        return new ResponseEntity<>(
                ApiResponse.success(
                        "Product added to cart successfully", cart
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update current user's cart item")
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemRequest request) {

        String username = authentication.getName();

        CartResponse cart = cartService.updateCartItem(
                username,
                cartItemId,
                request
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart item updated successfully", cart
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete current user's cart item")
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId) {

        String username = authentication.getName();

        cartService.deleteCartItem(
                username,
                cartItemId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart item deleted successfully",
                        null
                )
        );
    }
}
