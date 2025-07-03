package com.knowy.server.infrastructure.adapter.repository.mapper;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaPrivateUserEntity;
import com.knowy.server.infrastructure.adapter.repository.entity.JpaPublicUserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JpaPrivateUserMapper implements EntityMapper<PrivateUser, JpaPrivateUserEntity> {

	private static final String PUBLIC_USER_IS_REQUIRED = "The public user entity is required to be set to build a " +
		"domain private user";

	private final EntityMapper<PublicUser, JpaPublicUserEntity> publicUserMapper;

	public JpaPrivateUserMapper(EntityMapper<PublicUser, JpaPublicUserEntity> publicUserMapper) {
		this.publicUserMapper = publicUserMapper;
	}

	@Override
	public PrivateUser toDomain(JpaPrivateUserEntity entity) {
		JpaPublicUserEntity jpaPublicUserEntity = entity.getPublicUserEntity();
		Objects.requireNonNull(jpaPublicUserEntity, PUBLIC_USER_IS_REQUIRED);

		return new PrivateUser(
			publicUserMapper.toDomain(jpaPublicUserEntity),
			new Email(entity.getEmail()),
			new Password(entity.getPassword())
		);
	}

	@Override
	public JpaPrivateUserEntity toEntity(PrivateUser privateUser) {
		return new JpaPrivateUserEntity(
			privateUser.id(),
			privateUser.email().value(),
			privateUser.password().value(),
			publicUserMapper.toEntity(privateUser)
		);
	}

}
