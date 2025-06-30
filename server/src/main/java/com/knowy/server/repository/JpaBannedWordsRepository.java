package com.knowy.server.repository;

import com.knowy.server.entity.BannedWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBannedWordsRepository extends BannedWordsRepository, JpaRepository<BannedWordsEntity, Integer> {

	@Override
	public default List<BannedWordsEntity> findAll() {
		return List.of();
	}
}
