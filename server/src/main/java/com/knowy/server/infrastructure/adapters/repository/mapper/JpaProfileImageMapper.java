package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.infrastructure.adapters.repository.entity.ProfileImageEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaProfileImageMapper implements EntityMapper<ProfileImage, ProfileImageEntity> {
	@Override
	public ProfileImage toDomain(ProfileImageEntity entity) {
		return new ProfileImage(entity.getId(), entity.getUrl());
	}

	@Override
	public ProfileImageEntity toEntity(ProfileImage domain) {
		ProfileImageEntity profileImageEntity = new ProfileImageEntity();
		profileImageEntity.setId(domain.id());
		profileImageEntity.setUrl(domain.url());
		return profileImageEntity;
	}
}
