package com.example.project_bsdb.controller;


import com.example.project_bsdb.dto.Request.OrderRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.OrderResponse;
import com.example.project_bsdb.enums.OrderStatus;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order Management APIs")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication
    ) {

        OrderResponse created = orderService.createOrder(
                request,
                authentication.getName()
        );

        return new ResponseEntity<>(
                ApiResponse.success(
                        "Order placed successfully", created
                ),
                HttpStatus.CREATED
        );
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get my orders")
    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByName(
            Authentication authentication
    ) {

        List<OrderResponse> orders =
                orderService.getOrderByUsername(
                        authentication.getName()
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "My orders retrieved successfully",
                        orders
                )
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {

        List<OrderResponse> orders =
                orderService.getAllOrders();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Orders retrieved successfully", orders
                )
        );
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Get orders by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId(
            @PathVariable Long userId
    ) {

        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Orders retrieved successfully for user", orders
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "Invalid order status: " + status
            );
        }

        OrderResponse updated = orderService.updateOrderStatus(id, orderStatus);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order status updated successfully", updated
                )
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @PathVariable Long id
    ) {

        orderService.deleteOrder(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order deleted successfully",
                        null
                )
        );
    }
}

