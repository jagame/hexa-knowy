package com.knowy.server.repository;

import com.knowy.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	void saveUser(UserEntity user);

	List<UserEntity> id(Long id);
}
