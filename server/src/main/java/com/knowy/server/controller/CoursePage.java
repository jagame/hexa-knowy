package com.knowy.server.controller;


import com.knowy.server.controller.dto.CourseDTO;
import com.knowy.server.controller.dto.LessonDTO;
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
			new LessonDTO(1, "Introducción", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(2, "¿Qué es Java y cómo funciona?", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(3, "Tipos de datos y variables", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(4, "Estructuras de control", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(5, "Bucles", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(6, "Métodos (funciones)", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(7, "Arreglos (arrays)", LessonDTO.LessonStatus.NEXT_LESSON),
			new LessonDTO(8, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(9, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(10, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED)
		);

		int lastLesson = -1;
		for(int i = 0; i < lessons.size(); i++) {
			if(lessons.get(i).getStatus() == LessonDTO.LessonStatus.COMPLETE) {
				lastLesson = i;
			}
		}
		course.setLessons(lessons);
		model.addAttribute("course", course);
		model.addAttribute("lastLesson", lastLesson);
		return "/pages/course-page";
	}
}