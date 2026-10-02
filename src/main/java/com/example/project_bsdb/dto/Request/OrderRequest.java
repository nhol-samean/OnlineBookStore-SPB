package com.example.project_bsdb.dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "address_id must not be null")
    private Long addressId;

    @NotEmpty(message = "order must contain at least one item")
    @Valid
    private List<OrderItemRequest> orderItems;
}
