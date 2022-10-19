package com.example.yachtRent.repository;

import com.example.yachtRent.entity.YachtEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YachtRepository extends CrudRepository<YachtEntity, Long> {
}
