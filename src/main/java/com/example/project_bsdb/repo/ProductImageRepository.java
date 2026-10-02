package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductId(Long productId);
    @Query("""
       SELECT COALESCE(MAX(pi.displayOrder), 0)
       FROM ProductImageEntity pi
       WHERE pi.product.id = :productId
       """)
    Integer findMaxDisplayOrderByProductId(@Param("productId") Long productId);
}
