package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.PaymentRequest;
import com.example.project_bsdb.dto.Response.PaymentResponse;
import com.example.project_bsdb.entities.OrderEntity;
import com.example.project_bsdb.entities.PaymentEntity;

import java.math.BigDecimal;

public class PaymentMapper {

    public static PaymentEntity toEntity(
            PaymentRequest request) {

        if (request == null) {
            return null;
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentMethod(request.getPaymentMethod());

        return payment;
    }


    public static PaymentResponse toResponseDto(
            PaymentEntity payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());

        response.setAmount(payment.getAmount());

        response.setPaymentMethod(payment.getPaymentMethod());

        response.setPaymentStatus(payment.getPaymentStatus());

        OrderEntity order = payment.getOrder();

        if (order != null) {
            response.setOrderId(
                    order.getId()
            );
        }

        return response;
    }
}
