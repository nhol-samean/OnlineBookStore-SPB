package com.example.project_bsdb.dto.Response;

import com.example.project_bsdb.enums.PaymentMethod;
import com.example.project_bsdb.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus  paymentStatus;

    private Long orderId;
}
