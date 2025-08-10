package com.knowy.server.infrastructure.adapters.persistence.mapper;

import com.knowy.server.domain.Option;
import com.knowy.server.infrastructure.adapters.persistence.entity.OptionEntity;
import org.springframework.stereotype.Component;

@Component
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
