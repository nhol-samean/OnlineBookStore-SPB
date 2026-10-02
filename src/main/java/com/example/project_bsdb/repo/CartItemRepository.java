package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long cartId);
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
}
