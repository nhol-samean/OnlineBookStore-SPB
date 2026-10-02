package com.example.project_bsdb.dto.Request;

import com.example.project_bsdb.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUpdateRequest {

    @NotNull(message = "payment method must not be null")
    private PaymentMethod paymentMethod;
}
