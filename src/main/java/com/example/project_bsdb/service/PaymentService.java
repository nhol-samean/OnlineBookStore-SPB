package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.PaymentRequest;
import com.example.project_bsdb.dto.Request.PaymentUpdateRequest;
import com.example.project_bsdb.dto.Response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByOrderId(Long orderId);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePayment(Long id, PaymentUpdateRequest request);

    PaymentResponse confirmPayment(Long id);

    void deletePayment(Long id);
}
