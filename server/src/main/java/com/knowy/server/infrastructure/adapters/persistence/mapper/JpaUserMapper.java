package com.knowy.server.infrastructure.adapters.persistence.mapper;

import com.knowy.server.domain.User;
import com.knowy.server.infrastructure.adapters.persistence.entity.PublicUserEntity;
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
		return new User(
			entity.getId(),
			entity.getNickname(),
			jpaProfileImageMapper.toDomain(entity.getProfileImage()),
			entity.getLanguages().stream()
				.map(jpaCategoryMapper::toDomain)
				.collect(Collectors.toSet())
		);
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
