package com.knowy.server.controller;


import com.knowy.server.controller.model.LessonDto;
import com.knowy.server.controller.model.LinkType;
import com.knowy.server.controller.model.LinksLessonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonController {

	@GetMapping("/lesson")
	public String showLesson(Model model) {
		LessonDto lesson = new LessonDto("JavaScript moderno", "100 horas", "https://media.licdn.com/dms/image/v2/D4D12AQG_8eaFpxIX8g/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1685624189443?e=2147483647&v=beta&t=sYff1Zh5dsatOSYyUiMR8zwn3VqkG3x55bwFbEo5D-g", "#", "Teoria de la leccion");
		model.addAttribute("lesson", lesson);



		// Array of links from each lesson?
		List<LinksLessonDto> LinksLessonList = new ArrayList<>();

		//External links
		LinksLessonList.add(new LinksLessonDto(
			"Ecosia - Buscador ecológico",
			"https://www.ecosia.org",
			LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"Wikipedia - Enciclopedia libre",
			"https://es.wikipedia.org",
			LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"MDN Web Docs - JavaScript",
			"https://developer.mozilla.org/es/docs/Web/JavaScript",
			LinkType.EXTERNAL, ""
		));

		//Downloadable documents
		LinksLessonList.add(new LinksLessonDto(
			"Guía de JavaScript ES6+",
			"/documents/javascript-es6-guide.pdf",
			LinkType.DOCUMENT, "javascript-es6-guide.pdf"
		));

		LinksLessonList.add(new LinksLessonDto(
			"Ejercicios prácticos",
			"/documents/javascript-exercises.zip",
			LinkType.DOCUMENT, "javascript-exercises.zip"
		));


		model.addAttribute("LinksLessonList", LinksLessonList);

		return "pages/lesson-explanation";

	}
}