package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findById(String id);
    Optional<CartEntity> findByUser_Username(String username);
}
