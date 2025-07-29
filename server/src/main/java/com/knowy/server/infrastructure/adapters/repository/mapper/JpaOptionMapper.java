package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.Option;
import com.knowy.server.infrastructure.adapters.repository.entity.OptionEntity;

public class JpaOptionMapper implements EntityMapper<Option, OptionEntity> {
	@Override
	public Option toDomain(OptionEntity entity) {
		return new Option(
			entity.getId(),
			entity.getOptionText(),
			entity.isCorrect()
		);
	}

	@Override
	public OptionEntity toEntity(Option domain) {
		return null;
	}
}
