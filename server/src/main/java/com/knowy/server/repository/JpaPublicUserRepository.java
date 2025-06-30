package com.knowy.server.repository;
import com.knowy.server.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaPublicUserRepository extends PublicUserRepository, JpaRepository<PublicUserEntity, Integer> {
	@Override
	default Optional<PublicUserEntity> findUserById(Integer id) {
		return findById(id);
	}
	@Override
	default void updateNickname(String nickname, int id){
		findUserById(id).ifPresent(user -> {
			user.setNickname(nickname);
			save(user);
		});
	}

	//TODO AÑADIR EXCEPCION?
	@Override
	default Optional<PublicUserEntity> findByUsername(String username) {
		return findByUsername(username);
	}

	@Override
	default boolean existsByUsername(String username) {
		return false;
	}
}
