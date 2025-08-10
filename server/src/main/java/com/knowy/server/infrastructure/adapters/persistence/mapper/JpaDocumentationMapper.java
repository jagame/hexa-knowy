package com.knowy.server.infrastructure.adapters.persistence.mapper;

import com.knowy.server.domain.Documentation;
import com.knowy.server.infrastructure.adapters.persistence.dao.JpaLessonDao;
import com.knowy.server.infrastructure.adapters.persistence.entity.DocumentationEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaDocumentationMapper implements EntityMapper<Documentation, DocumentationEntity> {

	private final JpaLessonDao jpaLessonDao;

	public JpaDocumentationMapper(JpaLessonDao jpaLessonDao) {
		this.jpaLessonDao = jpaLessonDao;
	}

	@Override
	public Documentation toDomain(DocumentationEntity entity) {
		return new Documentation(entity.getId(), entity.getTitle(), entity.getLink());
	}

	@Override
	public DocumentationEntity toEntity(Documentation domain) {
		return new DocumentationEntity(
			domain.id(),
			domain.title(),
			domain.link(),
			jpaLessonDao.findAllByDocumentationId(domain.id())
		);
	}
}
