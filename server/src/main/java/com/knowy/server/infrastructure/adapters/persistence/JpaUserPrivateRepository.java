package com.knowy.server.infrastructure.adapters.persistence;

import com.knowy.server.domain.UserPrivate;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.infrastructure.adapters.persistence.dao.JpaUserPrivateDao;
import com.knowy.server.infrastructure.adapters.persistence.entity.PrivateUserEntity;
import com.knowy.server.infrastructure.adapters.persistence.mapper.JpaUserPrivateMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserPrivateRepository implements UserPrivateRepository {

	private final JpaUserPrivateDao jpaUserPrivateDao;
	private final JpaUserPrivateMapper jpaUserPrivateMapper;

	public JpaUserPrivateRepository(JpaUserPrivateDao jpaUserPrivateDao, JpaUserPrivateMapper jpaUserPrivateMapper) {
		this.jpaUserPrivateDao = jpaUserPrivateDao;
		this.jpaUserPrivateMapper = jpaUserPrivateMapper;
	}

	@Override
	public Optional<UserPrivate> findById(int id) {
		return jpaUserPrivateDao.findById(id).map(jpaUserPrivateMapper::toDomain);
	}

	@Override
	public Optional<UserPrivate> findByEmail(String email) {
		return jpaUserPrivateDao.findByEmail(email).map(jpaUserPrivateMapper::toDomain);
	}

	@Override
	public UserPrivate save(UserPrivate user) {
		PrivateUserEntity privateUserEntity = jpaUserPrivateDao.save(jpaUserPrivateMapper.toEntity(user));
		return jpaUserPrivateMapper.toDomain(privateUserEntity);
	}

}
