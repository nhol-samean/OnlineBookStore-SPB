package com.example.project_bsdb.dto.Response;

import com.example.project_bsdb.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private Long userId;
    private String username;

    private Long addressId;

    private List<OrderItemResponse> orderItems;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
