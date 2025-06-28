package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.PublicUserEntity;

public class JpaPublicUserMapper implements EntityMapper<PublicUser, PublicUserEntity> {


	@Override
	public PublicUser toDomain(PublicUserEntity entity) {
		return null;
	}

	@Override
	public PublicUserEntity toEntity(PublicUser domain) {
		return new PublicUserEntity(
			domain.id(),
			domain.nickname()
		);
	}
}
