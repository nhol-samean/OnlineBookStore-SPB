package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.OrderItemRequest;
import com.example.project_bsdb.dto.Request.OrderRequest;
import com.example.project_bsdb.dto.Response.OrderResponse;
import com.example.project_bsdb.entities.*;
import com.example.project_bsdb.enums.OrderStatus;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.OrderMapper;
import com.example.project_bsdb.repo.AddressRepository;
import com.example.project_bsdb.repo.OrderRepository;
import com.example.project_bsdb.repo.ProductRepository;
import com.example.project_bsdb.repo.UserRepository;
import com.example.project_bsdb.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(
            OrderRequest request,
            String username
    ) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username: " + username
                        )
                );
        AddressEntity address = addressRepository
                .findByIdAndUserId(request.getAddressId(), user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found or does not belong to current user"
                        )
                );
        OrderEntity order = OrderMapper.toEntity(request);

        order.setUser(user);
        order.setAddress(address);

        List<OrderItemEntity> orderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : request.getOrderItems()) {

            ProductEntity product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found with id: " + itemRequest.getProductId()
                            )
                    );
            if (product.getStock() < itemRequest.getQuantity()) {

                throw new BadRequestException(
                        "Insufficient stock for product '"
                                + product.getName()
                                + "'. Available: "
                                + product.getStock()
                                + ", requested: "
                                + itemRequest.getQuantity()
                );
            }
            product.setStock(product.getStock() - itemRequest.getQuantity());

            productRepository.save(product);
            OrderItemEntity orderItem = new OrderItemEntity();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            BigDecimal price = product.getPrice();

            orderItem.setPrice(price);
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            orderItem.setTotalPricePerItem(itemTotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemTotal);
        }
        order.setOrderItems(orderItems);
        order.setTotal_amount(totalAmount);
        OrderEntity savedOrder = orderRepository.save(order);
        return OrderMapper.toResponseDto(savedOrder);
    }

    public List<OrderResponse> getOrderByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username: " + username
                        )
                );

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId
            );
        }

        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(
            Long id,
            OrderStatus status
    ) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        order.setStatus(status);

        OrderEntity updatedOrder = orderRepository.save(order);

        return OrderMapper.toResponseDto(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        orderRepository.delete(order);
    }
}
