package com.knowy.server.infrastructure.adapters.persistence.mapper;

import com.knowy.server.domain.UserPrivate;
import com.knowy.server.infrastructure.adapters.persistence.entity.PrivateUserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaUserPrivateMapper implements EntityMapper<UserPrivate, PrivateUserEntity> {

	private final JpaUserMapper jpaUserMapper;

	public JpaUserPrivateMapper(JpaUserMapper jpaUserMapper) {
		this.jpaUserMapper = jpaUserMapper;
	}

	@Override
	public UserPrivate toDomain(PrivateUserEntity entity) {
		return new UserPrivate(
			jpaUserMapper.toDomain(entity.getPublicUserEntity()),
			entity.getEmail(),
			entity.getPassword(),
			entity.isActive()
		);
	}

	@Override
	public PrivateUserEntity toEntity(UserPrivate domain) {
		PrivateUserEntity privateUserEntity = new PrivateUserEntity();
		privateUserEntity.setId(domain.id());
		privateUserEntity.setEmail(domain.email());
		privateUserEntity.setPassword(domain.password());
		privateUserEntity.setActive(domain.active());
		privateUserEntity.setPublicUserEntity(jpaUserMapper.toEntity(domain.cropToUser()));
		return privateUserEntity;
	}
}
