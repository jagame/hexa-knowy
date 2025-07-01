package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JPAPrivateUserRepository extends JpaRepository<PrivateUserEntity, Integer>, PrivateUserRepository{
	@Override
	@Query("SELECT p from PrivateUser p where p.email=:email")
	Optional<PrivateUserEntity> findByEmail(String email);
}