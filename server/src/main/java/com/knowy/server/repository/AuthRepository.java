package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<PrivateUserEntity, Long> {

	@Query("SELECT pu FROM PrivateUserEntity pu JOIN FETCH pu.publicUserEntity WHERE pu.email = :email")
	Optional<PrivateUserEntity> findUserByEmailWithPublicData(@Param("email") String email);
}