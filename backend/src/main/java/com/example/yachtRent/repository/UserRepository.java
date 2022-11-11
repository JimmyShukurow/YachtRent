package com.example.yachtRent.repository;


import com.example.yachtRent.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByIdAndToken(Long id, String token);

    Optional<UserEntity> findByUsername(String username);
}
