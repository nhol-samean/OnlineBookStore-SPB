package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.CartItemRequest;
import com.example.project_bsdb.dto.Response.CartResponse;
import com.example.project_bsdb.entities.CartEntity;
import com.example.project_bsdb.entities.CartItemEntity;
import com.example.project_bsdb.entities.ProductEntity;
import com.example.project_bsdb.entities.UserEntity;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import java.util.Optional;

import com.example.project_bsdb.mapper.CartMapper;
import com.example.project_bsdb.repo.CartItemRepository;
import com.example.project_bsdb.repo.CartRepository;
import com.example.project_bsdb.repo.ProductRepository;
import com.example.project_bsdb.repo.UserRepository;
import com.example.project_bsdb.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CartResponse getCart(String username) {

        CartEntity cart = cartRepository.findByUser_Username(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for user: " + username
                        )
                );

        return CartMapper.toResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponse addToCart(
            String username,
            CartItemRequest request
    ) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + username
                        )
                );
        ProductEntity product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product not found with id: " + request.getProductId()
                )
        );
        CartEntity cart = cartRepository.findByUser_Username(username)
                .orElseGet(() -> {

                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });
        Optional<CartItemEntity> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItemEntity cartItem = existingItem.get();
            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

            cartItemRepository.save(cartItem);

        } else {

            CartItemEntity cartItem = new CartItemEntity();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());

            cart.getCartItems().add(cartItem);

            cartItemRepository.save(cartItem);
        }

        return CartMapper.toResponseDto(cart);
    }

    @Override
    public CartResponse updateCartItem(
            String username,
            Long cartItemId,
            CartItemRequest request
    ) {
        CartEntity cart = cartRepository.findByUser_Username(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for user: " + username
                        )
                );
        CartItemEntity cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart item not found with id: "
                                                + cartItemId
                                )
                        );
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException(
                    "Cart item does not belong to this user's cart"
            );
        }
        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return CartMapper.toResponseDto(cart);
    }

    @Override
    public void deleteCartItem(
            String username,
            Long cartItemId
    ) {
        CartEntity cart = cartRepository.findByUser_Username(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for user: " + username
                        )
                );
        CartItemEntity cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart item not found with id: "
                                                + cartItemId
                                )
                        );
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException(
                    "Cart item does not belong to this user's cart"
            );
        }
        cartItemRepository.delete(cartItem);
    }
}

