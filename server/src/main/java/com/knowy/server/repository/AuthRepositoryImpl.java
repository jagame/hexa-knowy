package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepositoryContract {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<PrivateUserEntity> findUserByEmailWithPublicData(String email) {
		TypedQuery<PrivateUserEntity> q = em.createQuery(
			"SELECT privateUser FROM PrivateUserEntity privateUser JOIN FETCH privateUser.publicUserEntity WHERE privateUser.email = :email",
			PrivateUserEntity.class
		);
		q.setParameter("email", email);
		return q.getResultStream().findFirst();
	}
}
