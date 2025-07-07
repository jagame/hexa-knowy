package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class CourseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Column(name = "description", length = 250, nullable = false)
	private String description;

	@Column(name = "creator", length = 250, nullable = false)
	private String creator;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "course_languages",
		joinColumns = @JoinColumn(name = "id_course"),
		inverseJoinColumns = @JoinColumn(name = "id_language")
	)
	private List<LanguageEntity> languages = new ArrayList<>();

}
