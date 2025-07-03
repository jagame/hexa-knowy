package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaProfileImageEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaProfileImageMapper implements EntityMapper<ProfileImage, JpaProfileImageEntity> {

	@Override
	public ProfileImage toDomain(JpaProfileImageEntity entity) {
		return new ProfileImage(
			entity.getId(),
			entity.getUrl()
		);
	}

	@Override
	public JpaProfileImageEntity toEntity(ProfileImage domain) {
		return new JpaProfileImageEntity(
			domain.id(),
			domain.url()
		);
	}

}
