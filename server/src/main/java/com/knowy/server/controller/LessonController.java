package com.knowy.server.controller;


import com.knowy.server.controller.model.LessonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LessonController {
	private static int valoracion;

	@GetMapping("/lesson")
	public String showLesson(Model model) {
		LessonDto lesson = new LessonDto("JavaScript moderno", "100 horas", "https://media.licdn.com/dms/image/v2/D4D12AQG_8eaFpxIX8g/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1685624189443?e=2147483647&v=beta&t=sYff1Zh5dsatOSYyUiMR8zwn3VqkG3x55bwFbEo5D-g", "#", "Teoria de la leccion");
		model.addAttribute("lesson", lesson);
		return "pages/lesson-explanation";


	}
	@PostMapping("/feedback/submit") //he usado este controller porque estaba relacionado, si hace falta lo meto en otra pantalla
	public String submitEval(@RequestParam("dificultad") String dificultad, RedirectAttributes redirectAttributes) {
		System.out.println("Dificultad seleccionada por el usuario: " + dificultad);
		valoracion = Integer.parseInt(dificultad); //aquí invocaríamos un metodo de cada pregunta para guardar el feedback y luego procesarlo. Debería hacer dto? yo creo que debería ser el dto de la tarjeta guardada.
		redirectAttributes.addFlashAttribute("mensajeFeedback", "Gracias por tu feedback: " + dificultad);
		return "redirect:/"; //este redirect irá a la próxima tarjeta de la lección.
	}
}
