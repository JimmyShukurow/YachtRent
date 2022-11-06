package com.example.yachtRent.repository;

import com.example.yachtRent.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
    @Query(value = "SELECT * FROM roles WHERE roles.id IN (SELECT role_id FROM user_roles WHERE user_id=:userId)", nativeQuery = true)
    Optional<List<RoleEntity>> findRolesOfUser(@Param("userId") Long userId);

}
