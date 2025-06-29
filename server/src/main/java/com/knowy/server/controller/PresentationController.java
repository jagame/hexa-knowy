package com.knowy.server.controller;

import com.knowy.server.controller.dto.NewsDto;
import com.knowy.server.controller.dto.SolutionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PresentationController {

	@GetMapping("/")
	public String viewLandingPage(ModelMap interfaceScreen) {

		List<NewsDto> newsList = new ArrayList<>();
		// Create and add three items to the list (we simulate the data extracted from the database)
		NewsDto news1 = new NewsDto();
		news1.setTitle("Nuevo curso de React avanzado disponible");
		news1.setDate("15 de abril, 2025");
		news1.setText("Hemos lanzado un nuevo curso de React que cubre los últimos hooks, patrones y mejores prácticas");
		newsList.add(news1);

		NewsDto news2 = new NewsDto();
		news2.setTitle("Actualización de la plataforma");
		news2.setDate("10 de abril, 2025");
		news2.setText("Hemos mejorado la interfaz de usuario y añadido nuevas funcionalidades para hacer tu experiencia de aprendizaje más fluida.");
		newsList.add(news2);

		NewsDto news3 = new NewsDto();
		news3.setTitle("Colaboración con empresas tecnológicas");
		news3.setDate("2 de abril, 2025");
		news3.setText("Nos hemos asociado con importantes empresas del sector para ofrecer oportunidades laborales a nuestros estudiantes destacados.");
		newsList.add(news3);

		//And I send the list to the screen
		interfaceScreen.addAttribute("newsList", newsList);


		List<SolutionDto> solutions = new ArrayList<>();
		solutions.add(new SolutionDto("Tarjeta 1: JavaScript vs Java", "Pregunta tarjeta 1", "Solución tarjeta 1"));
		solutions.add(new SolutionDto("Tarjeta 2: PHP", "Pregunta tarjeta 2", "Solución tarjeta 2"));
		solutions.add(new SolutionDto("Tarjeta 3: Python", "Pregunta tarjeta 3", "Solución tarjeta 3"));
		interfaceScreen.addAttribute("solutions", solutions);

		return "pages/landing-page";
	}

	@GetMapping("/example")
	public String example() {
		return "pages/example";
	}
}
