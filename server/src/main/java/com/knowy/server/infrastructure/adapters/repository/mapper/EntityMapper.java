package com.knowy.server.infrastructure.adapters.repository.mapper;

/**
 * @param <T> The domain type
 * @param <E> The entity type
 */
public interface EntityMapper<T, E> {

	T toDomain(E entity) throws Exception;

	E toEntity(T domain) throws Exception;
}
