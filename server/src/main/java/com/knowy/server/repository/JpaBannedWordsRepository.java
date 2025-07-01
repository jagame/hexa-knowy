package com.knowy.server.repository;

import com.knowy.server.entity.BannedWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBannedWordsRepository extends BannedWordsRepository, JpaRepository<BannedWordsEntity, Integer> {

	@Override
	List<BannedWordsEntity> findAll();
}
