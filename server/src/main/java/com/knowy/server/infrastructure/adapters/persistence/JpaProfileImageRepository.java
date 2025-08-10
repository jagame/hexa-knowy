package com.knowy.server.infrastructure.adapters.persistence;

import com.knowy.server.domain.ProfileImage;
import com.knowy.server.application.ports.ProfileImageRepository;
import com.knowy.server.infrastructure.adapters.persistence.dao.JpaProfileImageDao;
import com.knowy.server.infrastructure.adapters.persistence.mapper.JpaProfileImageMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaProfileImageRepository implements ProfileImageRepository {

	private final JpaProfileImageDao jpaProfileImageDao;
	private final JpaProfileImageMapper jpaProfileImageMapper;

	public JpaProfileImageRepository(JpaProfileImageDao jpaProfileImageDao, JpaProfileImageMapper jpaProfileImageMapper) {
		this.jpaProfileImageDao = jpaProfileImageDao;
		this.jpaProfileImageMapper = jpaProfileImageMapper;
	}

	@Override
	public Optional<ProfileImage> findById(int id) {
		return jpaProfileImageDao.findById(id).map(jpaProfileImageMapper::toDomain);
	}
}
