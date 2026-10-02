package com.example.project_bsdb.repo;

import com.example.project_bsdb.entities.RoleEntity;
import com.example.project_bsdb.enums.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleUser name);
}
