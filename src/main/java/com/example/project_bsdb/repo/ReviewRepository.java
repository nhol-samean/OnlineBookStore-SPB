package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.ReviewEntity;
import com.example.project_bsdb.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
        List<ReviewEntity> findByProductId(Long productId);
        Optional<ReviewEntity> findByUserIdAndProductId(Long userId, Long productId);
}
