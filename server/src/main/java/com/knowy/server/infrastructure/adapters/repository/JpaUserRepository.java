package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.User;
import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserDao;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaUserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

	private final JpaUserDao jpaUserDao;
	private final JpaUserMapper jpaUserMapper;

	public JpaUserRepository(JpaUserDao jpaUserDao, JpaUserMapper jpaUserMapper) {
		this.jpaUserDao = jpaUserDao;
		this.jpaUserMapper = jpaUserMapper;
	}

	@Override
	public Optional<User> findById(Integer id) {
		return jpaUserDao.findById(id).map(jpaUserMapper::toDomain);
	}

	@Override
	public void updateNickname(String nickname, int id) {
		jpaUserDao.updateNickname(nickname, id);
	}

	@Override
	public User save(User user) {
		jpaUserDao.save(jpaUserMapper.toEntity(user));
		return user;
	}

	@Override
	public Optional<User> findByNickname(String nickname) {
		return jpaUserDao.findByNickname(nickname).map(jpaUserMapper::toDomain);
	}

	@Override
	public boolean existsByNickname(String nickname) {
		return jpaUserDao.existsByNickname(nickname);
	}
}
