package com.knowy.server.controller.dto;

import com.knowy.server.entity.PublicUserLessonEntity;

public record LessonDto(
        int id,
        String title,
        String image,
        String duration,
        LessonStatus status
) {

    public static LessonDto fromEntity(PublicUserLessonEntity userLesson) {
        return new LessonDto(
                userLesson.getLessonId(),
                userLesson.getLessonEntity().getTitle(),
                null,
                null,
                LessonDto.LessonStatus.fromString(userLesson.getStatus())
        );
    }

    public enum LessonStatus {
        COMPLETE,
        NEXT_LESSON,
        BLOCKED;

        public static LessonStatus fromString(String status) {
            return switch (status.toLowerCase()) {
                case "completed" -> LessonDto.LessonStatus.COMPLETE;
                case "in_progress" -> LessonDto.LessonStatus.NEXT_LESSON;
                default -> LessonDto.LessonStatus.BLOCKED;
            };
        }
    }
}


