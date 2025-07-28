package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.infrastructure.adapters.repository.entity.ProfileImageEntity;

public class JpaProfileImageMapper implements EntityMapper<ProfileImage, ProfileImageEntity> {
	@Override
	public ProfileImage toDomain(ProfileImageEntity entity) {
		ProfileImage profileImage = new ProfileImage();
		profileImage.setId(entity.getId());
		profileImage.setUrl(entity.getUrl());
		return profileImage;
	}

	@Override
	public ProfileImageEntity toEntity(ProfileImage domain) {
		ProfileImageEntity profileImageEntity = new ProfileImageEntity();
		profileImageEntity.setId(domain.id());
		profileImageEntity.setUrl(domain.url());
		return profileImageEntity;
	}
}
