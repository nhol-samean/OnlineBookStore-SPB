package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.PaymentRequest;
import com.example.project_bsdb.dto.Request.PaymentUpdateRequest;
import com.example.project_bsdb.dto.Response.PaymentResponse;
import com.example.project_bsdb.entities.OrderEntity;
import com.example.project_bsdb.entities.PaymentEntity;
import com.example.project_bsdb.enums.OrderStatus;
import com.example.project_bsdb.enums.PaymentStatus;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.PaymentMapper;
import com.example.project_bsdb.repo.OrderRepository;
import com.example.project_bsdb.repo.PaymentRepository;
import com.example.project_bsdb.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        OrderEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + request.getOrderId()
                        )
                );

        if (order.getTotal_amount() == null ||
                order.getTotal_amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new BadRequestException(
                    "Order total amount must be greater than 0"
            );
        }

        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {

            throw new BadRequestException(
                    "Payment already exists for order id: "
                            + request.getOrderId()
            );
        }

        PaymentEntity payment = PaymentMapper.toEntity(request);

        payment.setOrder(order);
        payment.setAmount(order.getTotal_amount());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        PaymentEntity saved = paymentRepository.save(payment);

        return PaymentMapper.toResponseDto(saved);
    }



    @Override
    public PaymentResponse getPaymentById(Long id) {

        PaymentEntity payment = paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found with id: " + id)
                        );

        return PaymentMapper.toResponseDto(payment);
    }


    @Override
    public PaymentResponse getPaymentByOrderId(
            Long orderId) {

        PaymentEntity payment = paymentRepository.findByOrderId(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found for order id: " + orderId)
                        );
        return PaymentMapper.toResponseDto(payment);
    }


    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse confirmPayment(Long id) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BadRequestException(
                    "Payment is already paid"
            );
        }

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException(
                    "Only pending payment can be confirmed"
            );
        }

        payment.setPaymentStatus(PaymentStatus.PAID);

        OrderEntity order = payment.getOrder();

        if (order != null) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
        }

        PaymentEntity updated = paymentRepository.save(payment);

        return PaymentMapper.toResponseDto(updated);
    }


    @Override
    public PaymentResponse updatePayment(
            Long id,
            PaymentUpdateRequest request) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BadRequestException(
                    "Paid payment cannot be updated"
            );
        }

        payment.setPaymentMethod(request.getPaymentMethod());

        PaymentEntity updated = paymentRepository.save(payment);

        return PaymentMapper.toResponseDto(updated);
    }


    @Override
    public void deletePayment(Long id) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {

            throw new BadRequestException(
                    "Paid payment cannot be deleted"
            );
        }

        paymentRepository.delete(payment);
    }
}

