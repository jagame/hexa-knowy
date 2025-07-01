package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.PrivateUserEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.PublicUserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JpaPrivateUserMapper implements EntityMapper<PrivateUser, PrivateUserEntity> {

	private static final String PUBLIC_USER_IS_REQUIRED = "The public user entity is required to be set to build a " +
		"domain private user";

	private final EntityMapper<PublicUser, PublicUserEntity> publicUserMapper;

	public JpaPrivateUserMapper(EntityMapper<PublicUser, PublicUserEntity> publicUserMapper) {
		this.publicUserMapper = publicUserMapper;
	}

	@Override
	public PrivateUser toDomain(PrivateUserEntity entity) {
		PublicUserEntity publicUserEntity = entity.getPublicUserEntity();
		Objects.requireNonNull(publicUserEntity, PUBLIC_USER_IS_REQUIRED);

		return new PrivateUser(
			publicUserMapper.toDomain(publicUserEntity),
			new Email(entity.getEmail()),
			new Password(entity.getPassword())
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
