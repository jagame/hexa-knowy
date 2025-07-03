package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaProfileImageEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaPublicUserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaPublicUserMapper implements EntityMapper<PublicUser, JpaPublicUserEntity> {

	private final EntityMapper<ProfileImage, JpaProfileImageEntity> profileImageMapper;

	public JpaPublicUserMapper(EntityMapper<ProfileImage, JpaProfileImageEntity> profileImageMapper) {
		this.profileImageMapper = profileImageMapper;
	}

	@Override
	public PublicUser toDomain(JpaPublicUserEntity entity) {
		return null;
	}

	@Override
	public JpaPublicUserEntity toEntity(PublicUser domain) {
		return new JpaPublicUserEntity(
			domain.id(),
			domain.nickname(),
			profileImageMapper.toEntity(domain.profileImage())
		);
	}
}
