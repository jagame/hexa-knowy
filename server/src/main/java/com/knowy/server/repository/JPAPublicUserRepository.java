package com.knowy.server.repository;

import com.knowy.server.entity.PublicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JPAPublicUserRepository extends JpaRepository<PublicUser, Integer>, PublicUserRepository {
	@Override
	@Query("SELECT u FROM PublicUser u WHERE u.nickname=:nickname")
	Optional<PublicUser> findByNickname(String nickname);


}