package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "author", length = 250, nullable = false)
	private String author;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "course_language",
		joinColumns = @JoinColumn(name = "id_course"),
		inverseJoinColumns = @JoinColumn(name = "id_language")
	)
	private List<LanguageEntity> languages = new ArrayList<>();

}
