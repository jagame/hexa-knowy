package com.knowy.server.controller;


import com.knowy.server.controller.model.LessonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonController {

	@GetMapping("/lesson")
	public String showLesson(Model model) {
		LessonDto lesson = new LessonDto("JavaScript moderno", "100 horas", "https://media.licdn.com/dms/image/v2/D4D12AQG_8eaFpxIX8g/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1685624189443?e=2147483647&v=beta&t=sYff1Zh5dsatOSYyUiMR8zwn3VqkG3x55bwFbEo5D-g", "#", "Teoria de la leccion");
		model.addAttribute("lesson", lesson);
		return "pages/lesson-explanation";
	}
}