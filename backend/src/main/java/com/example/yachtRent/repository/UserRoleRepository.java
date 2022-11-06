package com.example.yachtRent.repository;

import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);


}
