package com.knowy.server.infrastructure.adapter.repository;

import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.infrastructure.adapter.repository.dao.PrivateUserEntityDao;
import com.knowy.server.infrastructure.adapter.repository.mapper.JpaPrivateUserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class JpaPrivateUserRepository implements PrivateUserRepository {

	private final PrivateUserEntityDao privateUserDao;
	private final JpaPrivateUserMapper privateUserMapper;

	public JpaPrivateUserRepository(PrivateUserEntityDao privateUserDao, JpaPrivateUserMapper privateUserMapper) {
		this.privateUserDao = privateUserDao;
		this.privateUserMapper = privateUserMapper;
	}

	@Override
	public Optional<PrivateUser> findByEmail(String email) {
		return privateUserDao.findByEmail(email)
			.map(privateUserMapper::toDomain);
	}

	@Override
	public Optional<PrivateUser> findByToken(String token) {
		return privateUserDao.findByToken(token)
			.map(privateUserMapper::toDomain);
	}

	@Override
	public void update(PrivateUser privateUser) {
		privateUserDao.save(privateUserMapper.toEntity(privateUser));
	}
}
