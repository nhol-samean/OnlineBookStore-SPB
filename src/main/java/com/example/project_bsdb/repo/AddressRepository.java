package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByUserId(Long userId);
    Optional<AddressEntity> findByIdAndUserId(Long id, Long userId);

}
