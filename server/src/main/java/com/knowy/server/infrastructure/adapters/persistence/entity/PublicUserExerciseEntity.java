package com.knowy.server.infrastructure.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_user_exercise")
public class PublicUserExerciseEntity {

	@EmbeddedId
	private PublicUserExerciseId id;

	@ColumnDefault("0")
	@Column(name = "rate", nullable = false)
	private Integer rate;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "next_review", nullable = false)
	private LocalDateTime nextReview;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_public_user", referencedColumnName = "id", insertable = false, updatable = false)
	private PublicUserEntity publicUserEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_exercise", referencedColumnName = "id", insertable = false, updatable = false)
	private ExerciseEntity exerciseEntity;

	public PublicUserExerciseEntity(int userId, int exerciseId) {
		this.id = new PublicUserExerciseId(userId, exerciseId);
		this.rate = 0;
		this.nextReview = LocalDateTime.now();
	}

	public void setRate(int rate) {
		this.rate = Math.clamp(rate, 0, 100);
	}
}
