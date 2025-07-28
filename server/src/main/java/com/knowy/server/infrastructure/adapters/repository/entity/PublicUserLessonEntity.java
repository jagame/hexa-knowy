package com.knowy.server.infrastructure.adapters.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "public_user_lesson")
@IdClass(PublicUserLessonIdEntity.class)
public class PublicUserLessonEntity {
	@Id
	@Column(name = "id_public_user", nullable = false)
	private Integer userId;

	@Id
	@Column(name = "id_lesson", nullable = false)
	private Integer lessonId;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "status", nullable = false)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_public_user", referencedColumnName = "id", insertable = false, updatable = false)
	private PublicUserEntity publicUserEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_lesson", referencedColumnName = "id", insertable = false, updatable = false)
	private LessonEntity lessonEntity;
}
