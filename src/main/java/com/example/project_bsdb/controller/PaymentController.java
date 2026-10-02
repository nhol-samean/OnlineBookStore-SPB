package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.PaymentRequest;
import com.example.project_bsdb.dto.Request.PaymentUpdateRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.PaymentResponse;
import com.example.project_bsdb.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment", description = "Payment Management APIs")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new payment")
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse payment = paymentService.createPayment(request);

        return new ResponseEntity<>(
                ApiResponse.success(
                        "Payment created successfully", payment
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get payment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long id) {

        PaymentResponse payment = paymentService.getPaymentById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment retrieved successfully", payment
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get payment by order ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByOrderId(
            @PathVariable Long orderId) {

        PaymentResponse payment = paymentService.getPaymentByOrderId(orderId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment retrieved successfully", payment
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all payments")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {

        List<PaymentResponse> payments = paymentService.getAllPayments();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments retrieved successfully", payments
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update payment by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentUpdateRequest request) {

        PaymentResponse updated =
                paymentService.updatePayment(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment updated successfully",
                        updated
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Confirm payment")
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<PaymentResponse>> confirmPayment(
            @PathVariable Long id) {

        PaymentResponse payment =
                paymentService.confirmPayment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment confirmed successfully",
                        payment
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete payment by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment deleted successfully",
                        null
                )
        );
    }
}
