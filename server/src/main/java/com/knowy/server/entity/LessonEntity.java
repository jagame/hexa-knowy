package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class LessonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_course", nullable = false)
	private CourseEntity course;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_next_lesson")
	private LessonEntity nextLesson;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Column(name = "explanation", length = 250, nullable = false)
	private String explanation;

	@ManyToMany
	@JoinTable(
		name = "lesson_documentation",
		joinColumns = @JoinColumn(name = "id_lesson"),
		inverseJoinColumns = @JoinColumn(name = "id_documentation")
	)
	private List<DocumentationEntity> documentations;

	@OneToMany(mappedBy = "lesson")
	private List<ExerciseEntity> exercises = new ArrayList<>();

}
