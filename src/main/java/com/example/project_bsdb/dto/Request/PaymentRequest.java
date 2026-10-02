package com.example.project_bsdb.dto.Request;

import com.example.project_bsdb.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "order_id must not be null")
    private Long orderId;

    @NotNull(message = "payment_method must not be null")
    private PaymentMethod paymentMethod;
}
