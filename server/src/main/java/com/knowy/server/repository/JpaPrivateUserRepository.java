package com.knowy.server.repository;
import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaPrivateUserRepository extends PrivateUserRepository, JpaRepository<PrivateUserEntity, Integer> {
	@Override
	PrivateUserEntity findByEmail(String email);

	@Override
	default void updateEmail(String email, String newEmail){
		PrivateUserEntity usuario = findByEmail(email);
		usuario.setEmail(newEmail);
		save(usuario);
	}
}
