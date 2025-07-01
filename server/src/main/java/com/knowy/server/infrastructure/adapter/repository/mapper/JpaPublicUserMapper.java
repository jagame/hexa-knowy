package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.ProfileImageEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.PublicUserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaPublicUserMapper implements EntityMapper<PublicUser, PublicUserEntity> {

	private final EntityMapper<ProfileImage, ProfileImageEntity> profileImageMapper;

	public JpaPublicUserMapper(EntityMapper<ProfileImage, ProfileImageEntity> profileImageMapper) {
		this.profileImageMapper = profileImageMapper;
	}

	@Override
	public PublicUser toDomain(PublicUserEntity entity) {
		return null;
	}

	@Override
	public PublicUserEntity toEntity(PublicUser domain) {
		return new PublicUserEntity(
			domain.id(),
			domain.nickname(),
			profileImageMapper.toEntity(domain.profileImage())
		);
	}
}
