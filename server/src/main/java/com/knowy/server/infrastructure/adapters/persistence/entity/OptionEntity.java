package com.knowy.server.infrastructure.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option")
public class OptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_exercise", nullable = false)
	private ExerciseEntity exercise;

	@Column(name = "option_text", nullable = false)
	private String optionText;

	@Column(name = "is_correct", nullable = false)
	private boolean isCorrect;
}
