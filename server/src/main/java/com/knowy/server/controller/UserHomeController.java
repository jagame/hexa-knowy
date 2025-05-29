package com.knowy.server.controller;

import com.knowy.server.controller.model.NewsHomeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeController {

	@GetMapping("/home")
	public String userHome(Model model) {
		List<NewsHomeDto> newsHome = new ArrayList<>();
		newsHome.add(new NewsHomeDto(1, "La conspiración de los grillos", "Una historia que no deja dormir",
			"https://placehold.org/900x900/ff6f61?text=900x900", "https://placehold.org/900x900/ff6f61?text=900x900"));

		newsHome.add(new NewsHomeDto(2, "El código de las sombras", "Tecnología, misterio y traición",
			"https://placehold.org/900x900/6b5b95?text=900x900", "https://placehold.org/900x900/6b5b95?text=900x900"));

		newsHome.add(new NewsHomeDto(3, "Viaje al centro del café", "Una travesía entre aromas y secretos",
			"https://placehold.org/900x900/88b04b?text=900x900", "https://placehold.org/900x900/88b04b?text=900x900"));

		newsHome.add(new NewsHomeDto(4, "No abras la puerta roja", "Suspenso psicológico en cada página",
			"https://placehold.org/900x900/d65076?text=900x900", "https://placehold.org/900x900/d65076?text=900x900"));

		newsHome.add(new NewsHomeDto(5, "La física del silencio", "Cuando el universo conspira para callar",
			"https://placehold.org/900x900/45b8ac?text=900x900", "https://placehold.org/900x900/45b8ac?text=900x900"));


		model.addAttribute("newsHome", newsHome);
		return "pages/user-home";
	}

}
