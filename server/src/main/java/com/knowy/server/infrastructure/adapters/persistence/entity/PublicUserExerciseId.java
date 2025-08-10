package com.knowy.server.infrastructure.adapters.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PublicUserExerciseId implements Serializable {

	@Column(name = "id_public_user", nullable = false)
	private Integer idPublicUser;

	@Column(name = "id_exercise", nullable = false)
	private Integer idExercise;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		PublicUserExerciseId entity = (PublicUserExerciseId) o;
		return Objects.equals(this.idExercise, entity.idExercise) &&
			Objects.equals(this.idPublicUser, entity.idPublicUser);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idExercise, idPublicUser);
	}

}