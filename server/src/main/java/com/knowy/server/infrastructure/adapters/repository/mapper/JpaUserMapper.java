package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.User;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JpaUserMapper implements EntityMapper<User, PublicUserEntity> {

	private final JpaCategoryMapper jpaCategoryMapper;
	private final JpaProfileImageMapper jpaProfileImageMapper;

	public JpaUserMapper(JpaCategoryMapper jpaCategoryMapper, JpaProfileImageMapper jpaProfileImageMapper) {
		this.jpaCategoryMapper = jpaCategoryMapper;
		this.jpaProfileImageMapper = jpaProfileImageMapper;
	}

	@Override
	public User toDomain(PublicUserEntity entity) {
		User user = new User();
		user.setId(entity.getId());
		user.setNickname(entity.getNickname());
		user.setProfileImage(jpaProfileImageMapper.toDomain(entity.getProfileImage()));
		user.setCategories(entity.getLanguages().stream()
			.map(jpaCategoryMapper::toDomain)
			.collect(Collectors.toSet())
		);
		return user;
	}

	@Override
	public PublicUserEntity toEntity(User domain) {
		PublicUserEntity publicUserEntity = new PublicUserEntity();
		publicUserEntity.setId(domain.id());
		publicUserEntity.setNickname(domain.nickname());
		publicUserEntity.setProfileImage(jpaProfileImageMapper.toEntity(domain.profileImage()));
		publicUserEntity.setLanguages(domain.categories().stream()
			.map(jpaCategoryMapper::toEntity)
			.collect(Collectors.toSet())
		);
		return publicUserEntity;
	}
}
