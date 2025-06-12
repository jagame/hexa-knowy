package com.knowy.server.controller;


import com.knowy.server.controller.model.LessonDto;
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



		// Array de Links de ¿cada lección?
		List<LinksLessonDto> LinksLessonList = new ArrayList<>();
		LinksLessonDto url1 = new LinksLessonDto();
		url1.setLinkTitle("Ecosia");
		url1.setLinkUrl("https://www.ecosia.org");

		LinksLessonDto url2 = new LinksLessonDto();
		url2.setLinkTitle("Wikipedia");
		url2.setLinkUrl("https://es.wikipedia.org");

		LinksLessonList.add(url1);
		LinksLessonList.add(url2);
		model.addAttribute("LinksLessonList", LinksLessonList);


		return "pages/lesson-explanation";

	}
}