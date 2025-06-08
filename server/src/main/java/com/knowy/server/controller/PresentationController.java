package com.knowy.server.controller;

import com.knowy.server.controller.model.NewsDto;
import com.knowy.server.controller.model.NewsHomeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
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

		//Test carousel-news
		List<NewsHomeDto> newsHome = Arrays.asList(new NewsHomeDto(
				1,
				"Python",
				"Nuevo curso Python",
				"https://i0.wp.com/junilearning.com/wp-content/uploads/2020/06/python-programming-language.webp?fit=800%2C800&ssl=1",
				"#"),
			new NewsHomeDto(
				2,
				"HTML5",
				"Crea páginas web con HTML5",
				"https://t3.ftcdn.net/jpg/05/78/11/34/360_F_578113446_j72wZX7u698eoV50XXmkLMH7gGuNhpVB.jpg",
				"#"),
			new NewsHomeDto(3,
				"JavaScript",
				"Aprende JavaScript",
				"https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Unofficial_JavaScript_logo_2.svg/2048px-Unofficial_JavaScript_logo_2.svg.png",
				"#"));
		interfaceScreen.addAttribute("newsHome", newsHome);
		return "pages/landing-page";
	}

	@GetMapping("/example")
	public String example() {
		return "pages/example";
	}

}
