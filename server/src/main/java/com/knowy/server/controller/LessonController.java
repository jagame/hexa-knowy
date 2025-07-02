package com.knowy.server.controller;


import com.knowy.server.controller.dto.CourseDTO;
import com.knowy.server.controller.dto.LessonDTO;
import com.knowy.server.controller.dto.LinksLessonDto;
import com.knowy.server.controller.dto.SolutionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonController {
	private static int valoracion;

	@GetMapping("/lesson")
	public String showLesson(Model model) {

		LessonDTO lesson = new LessonDTO(
			1,
			"JavaScript moderno",
			"https://picsum.photos/seed/picsum/1000/1000",
			2,
			LessonDTO.LessonStatus.BLOCKED
		);
		model.addAttribute("lesson", lesson);

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
		for(int i = 0; i < lessons.size(); i++) {
			if(lessons.get(i).getStatus() == LessonDTO.LessonStatus.COMPLETE) {
				lastLesson = i;
			}
		}

		String lessonContent= "<p>En <strong>Java</strong>, un arreglo es una estructura de datos que permite "
			+ "<strong>almacenar múltiples elementos del mismo tipo</strong> en una sola variable.</p>\n"
			+ "<p>Los arreglos tienen <strong>tamaño fijo</strong> (no se pueden expandir una vez creados) "
			+ "y cada elemento se accede mediante un <strong>índice que empieza en 0</strong>.</p>\n"
			+ "<p>Se declaran y crean así:</p>\n"
			+ "<h6>int[] numeros = new int[5]; // arreglo de 5 enteros</h6>\n"
			+ "<p>o con valores definidos:</p>\n"
			+ "<h6>String[] nombres = {\"Ana\", \"Luis\", \"Pedro\"};</h6>";
		
		course.setLessons(lessons);
		model.addAttribute("course", course);
		model.addAttribute("lastLesson", lastLesson);
		model.addAttribute("lessonContent", lessonContent);

		//---------------------------------------------------------
		List<SolutionDto> solutions = new ArrayList<>();
		solutions.add(new SolutionDto("Tarjeta 1: JavaScript vs Java", "Pregunta tarjeta 1", "Solución tarjeta 1"));
		solutions.add(new SolutionDto("Tarjeta 2: PHP", "Pregunta tarjeta 2", "Solución tarjeta 2"));
		solutions.add(new SolutionDto("Tarjeta 3: Python", "Pregunta tarjeta 3", "Solución tarjeta 3"));
		model.addAttribute("solutions", solutions);

		//--------------------





		// Array of links from each lesson?
		List<LinksLessonDto> LinksLessonList = new ArrayList<>();

		//External links
		LinksLessonList.add(new LinksLessonDto(
			"Ecosia - Buscador ecológico",
			"https://www.ecosia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"Wikipedia - Enciclopedia libre",
			"https://es.wikipedia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"MDN Web Docs - JavaScript",
			"https://developer.mozilla.org/es/docs/Web/JavaScript",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		//Downloadable documents
		LinksLessonList.add(new LinksLessonDto(
			"Guía de JavaScript ES6+",
			"/documents/javascript-es6-guide.pdf",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-es6-guide.pdf"
		));

		LinksLessonList.add(new LinksLessonDto(
			"Ejercicios prácticos",
			"/documents/javascript-exercises.zip",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-exercises.zip"
		));


		model.addAttribute("LinksLessonList", LinksLessonList);

		return "pages/lesson-explanation";
	}

	@PostMapping("/feedback/submit")
	//he usado este controller porque estaba relacionado, si hace falta lo meto en otra pantalla
	public String submitEval(@RequestParam("dificultad") String dificultad, RedirectAttributes redirectAttributes) {
		System.out.println("Dificultad seleccionada por el usuario: " + dificultad);
		valoracion = Integer.parseInt(dificultad); //aquí invocaríamos un metodo de cada pregunta para guardar el feedback y luego procesarlo. Debería hacer dto? yo creo que debería ser el dto de la tarjeta guardada.
		redirectAttributes.addFlashAttribute("mensajeFeedback", "Gracias por tu feedback: " + dificultad);
		return "redirect:/"; //este redirect irá a la próxima tarjeta de la lección.
	}
}