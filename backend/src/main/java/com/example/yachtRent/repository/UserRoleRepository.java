package com.example.yachtRent.repository;

import com.example.yachtRent.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);
}
