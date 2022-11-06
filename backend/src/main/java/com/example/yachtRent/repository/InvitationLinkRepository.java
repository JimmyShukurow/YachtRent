package com.example.yachtRent.repository;

import com.example.yachtRent.entity.InvitationLinkEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InvitationLinkRepository extends CrudRepository<InvitationLinkEntity, Long> {

    InvitationLinkEntity findByHash(String hash);
}
