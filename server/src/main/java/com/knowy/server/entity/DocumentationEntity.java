package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documentation")
public class DocumentationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Column(name = "link", nullable = false)
	private String link;

	@ManyToMany(mappedBy = "documentations", fetch = FetchType.LAZY)
	private List<LessonEntity> lessons;
}
