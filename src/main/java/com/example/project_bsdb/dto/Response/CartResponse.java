package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private Long id;

    private Long userId;
    private String username;

    private List<CartItemResponse> items;

    private BigDecimal totalAmount;
}
