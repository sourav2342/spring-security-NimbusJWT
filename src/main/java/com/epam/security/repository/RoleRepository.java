package com.epam.security.repository;

import com.epam.security.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String role);
}
