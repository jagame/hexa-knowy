package com.knowy.server.infrastructure.adapter.repository;

import com.knowy.server.infrastructure.adapter.repository.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPublicUserRepository extends JpaRepository<PublicUserEntity, Integer> {

}
