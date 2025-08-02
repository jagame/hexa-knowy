package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.Course;
import com.knowy.server.application.ports.CourseRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaCourseDao;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaCourseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCourseRepository implements CourseRepository {

	private final JpaCourseDao jpaCourseDao;
	private final JpaCourseMapper jpaCourseMapper;

	public JpaCourseRepository(JpaCourseDao jpaCourseDao, JpaCourseMapper jpaCourseMapper) {
		this.jpaCourseDao = jpaCourseDao;
		this.jpaCourseMapper = jpaCourseMapper;
	}

	@Override
	public List<Course> findByIdIn(List<Integer> ids) {
		return jpaCourseDao.findByIdIn(ids)
			.stream()
			.map(jpaCourseMapper::toDomain)
			.toList();
	}

	@Override
	public List<Course> findAll() {
		return jpaCourseDao.findAll()
			.stream()
			.map(jpaCourseMapper::toDomain)
			.toList();
	}

	@Override
	public Optional<Course> findById(Integer id) {
		return jpaCourseDao.findById(id).map(jpaCourseMapper::toDomain);
	}

	@Override
	public List<Course> findAllRandom() {
		return jpaCourseDao.findAllRandom()
			.stream()
			.map(jpaCourseMapper::toDomain)
			.toList();
	}
}
