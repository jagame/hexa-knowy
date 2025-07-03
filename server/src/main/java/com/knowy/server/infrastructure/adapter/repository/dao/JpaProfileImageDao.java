package com.knowy.server.infrastructure.adapter.repository.dao;

import com.knowy.server.infrastructure.adapter.repository.entity.JpaProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProfileImageDao extends JpaRepository<JpaProfileImageEntity, Integer> {
}
