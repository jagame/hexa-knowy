package com.knowy.server.infrastructure.adapter.repository.mapper;

/**
 *
 * @param <T> The domain type
 * @param <E> The entity type
 */
public interface EntityMapper<T, E> {

	T toDomain(E entity);

	E toEntity(T domain);

}
