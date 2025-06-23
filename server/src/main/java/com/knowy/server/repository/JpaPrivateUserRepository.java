package com.knowy.server.repository;
import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaPrivateUserRepository extends PrivateUserRepository, JpaRepository<PrivateUserEntity, Integer> {
	@Override
	default PrivateUserEntity findByEmail(String email){
		return findByEmail(email);
	}

	@Override
	default void updateEmail(String email){
		PrivateUserEntity usuario = findByEmail(email);
		usuario.setEmail(email);
		save(usuario);
	}
}
