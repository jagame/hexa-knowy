package com.knowy.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserLessonIdEntity implements Serializable {
	private Integer userId;
	private Integer lessonId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PublicUserLessonIdEntity that)) return false;
		return Objects.equals(userId, that.userId) &&
			Objects.equals(lessonId, that.lessonId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, lessonId);
	}
}
