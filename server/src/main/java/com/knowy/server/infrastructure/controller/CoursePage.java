package com.knowy.server.infrastructure.controller;


import com.knowy.server.infrastructure.controller.dto.CourseDTO;
import com.knowy.server.infrastructure.controller.dto.LessonDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoursePage {

	@GetMapping("/course")
	public String showCourse(Model model) {
		CourseDTO course = new CourseDTO();
		course.setName("Java Básico");
		course.setPercentageCompleted(40);

		List<LessonDTO> lessons = List.of(
			new LessonDTO(1, "Introducción", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(2, "¿Qué es Java y cómo funciona?", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(3, "Tipos de datos y variables", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(4, "Estructuras de control", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(5, "Bucles", "https://picsum.photos/seed/picsum/200/300", 2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(6, "Métodos (funciones)", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(7, "Arreglos (arrays)", "https://picsum.photos/seed/picsum/200/300",
				2, LessonDTO.LessonStatus.NEXT_LESSON),
			new LessonDTO(8, "Introducción a la Programación Orientada a Objetos", "https://picsum" +
				".photos/seed/picsum/200/300", 2, LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(9, "Introducción a la Programación Orientada a Objetos", "https://picsum" +
				".photos/seed/picsum/200/300", 2, LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(10, "Introducción a la Programación Orientada a Objetos", "https://picsum" +
				".photos/seed/picsum/200/300", 2, LessonDTO.LessonStatus.BLOCKED)
		);

		int lastLesson = -1;
		for (int i = 0; i < lessons.size(); i++) {
			if (lessons.get(i).getStatus() == LessonDTO.LessonStatus.COMPLETE) {
				lastLesson = i;
			}
		}
		course.setLessons(lessons);
		model.addAttribute("course", course);
		model.addAttribute("lastLesson", lastLesson);
		return "/pages/course-page";
	}
}