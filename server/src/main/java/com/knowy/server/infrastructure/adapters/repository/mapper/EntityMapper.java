package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.exception.KnowyException;

/**
 * @param <T> The domain type
 * @param <E> The entity type
 */
public interface EntityMapper<T, E> {

	T toDomain(E entity) throws KnowyException;

	E toEntity(T domain) throws KnowyException;
}
