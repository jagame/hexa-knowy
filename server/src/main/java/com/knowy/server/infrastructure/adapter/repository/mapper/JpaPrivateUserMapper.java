package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.PrivateUserEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.PublicUserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaPrivateUserMapper implements EntityMapper<PrivateUser, PrivateUserEntity> {

	private final EntityMapper<PublicUser, PublicUserEntity> publicUserMapper;

	public JpaPrivateUserMapper(EntityMapper<PublicUser, PublicUserEntity> publicUserMapper) {
		this.publicUserMapper = publicUserMapper;
	}

	@Override
	public PrivateUser toDomain(PrivateUserEntity entity) {
		return new PrivateUser(
			entity.getId(),
			entity.getPublicUserEntity().getNickname(),
			entity.getEmail(),
			entity.getPassword()
		);
	}

	@Override
	public PrivateUserEntity toEntity(PrivateUser privateUser) {
		return new PrivateUserEntity(
			privateUser.id(),
			privateUser.email().value(),
			privateUser.password().value(),
			publicUserMapper.toEntity(privateUser)
		);
	}

}
