package com.knowy.server.repository;

import com.knowy.server.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PublicUserEntity, Long> {

	Optional<PublicUserEntity> findByUsername(String username);

	boolean existsByUsername(String username);
}
