package com.knowy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "public_user_exercise")
public class PublicUserExerciseEntity {

	@NotNull
	@ColumnDefault("0")
	@Column(name = "rate", nullable = false)
	private int rate;

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "next_review", nullable = false)
	private LocalDateTime nextReview;

	@EmbeddedId
	private PublicUserExerciseId id;

	public void setRate(int rate) {
		this.rate = Math.clamp(rate, 0, 100);
	}
}
