package com.knowy.server.controller;

import com.knowy.server.controller.dto.MissionsDto;
import com.knowy.server.controller.dto.NewsHomeDto;
import com.knowy.server.service.UserHomeService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeController {

	private final UserHomeService userHomeService;

	public UserHomeController(UserHomeService userHomeService) {
		this.userHomeService = userHomeService;
	}

	@GetMapping("/home")
	public String userHome(Model model, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		Integer userId = userDetails.getPublicUser().getId();
		long coursesCompleted = userHomeService.getCoursesCompleted(userId);
		long totalCourses = userHomeService.getTotalCourses(userId);
		long percent = userHomeService.getCoursesPercentage(userId);

		model.addAttribute("completedCourses", coursesCompleted);
		model.addAttribute("totalCourses", totalCourses);
		model.addAttribute("fractionProgress", percent);


		List<NewsHomeDto> newsHome = new ArrayList<>();
		newsHome.add(new NewsHomeDto(1, "La conspiración de los grillos", "Una historia que no deja dormir",
			"https://picsum.photos/id/10/900/900", "https://picsum.photos/id/10/900/900"));

		newsHome.add(new NewsHomeDto(2, "El código de las sombras", "Tecnología, misterio y traición",
			"https://picsum.photos/id/20/900/900", "https://picsum.photos/id/20/900/900"));

		newsHome.add(new NewsHomeDto(3, "Viaje al centro del café", "Una travesía entre aromas y secretos",
			"https://picsum.photos/id/30/900/900", "https://picsum.photos/id/30/900/900"));

		newsHome.add(new NewsHomeDto(4, "No abras la puerta roja", "Suspenso psicológico en cada página",
			"https://picsum.photos/id/40/900/900", "https://picsum.photos/id/40/900/900"));

		newsHome.add(new NewsHomeDto(5, "La física del silencio", "Cuando el universo conspira para callar",
			"https://picsum.photos/id/50/900/900", "https://picsum.photos/id/50/900/900"));


		model.addAttribute("newsHome", newsHome);
		model.addAttribute("username", userDetails.getPublicUser().getNickname());

		List<MissionsDto> missionsList = new ArrayList<>();
		MissionsDto mission1 = new MissionsDto();
		mission1.setName("Completa 3 lecciones");
		mission1.setCurrentProgress(2);
		mission1.setTotalProgress(3);

		missionsList.add(mission1);
		MissionsDto mission2 = new MissionsDto();
		mission2.setName("Completa 4 lecciones");
		mission2.setCurrentProgress(2);
		mission2.setTotalProgress(4);

		missionsList.add(mission2);
		MissionsDto mission3 = new MissionsDto();
		mission3.setName("Completa 5 lecciones");
		mission3.setCurrentProgress(2);
		mission3.setTotalProgress(5);

		missionsList.add(mission3);

		model.addAttribute("missionsList", missionsList);

//		List<MissionsDto> languageOptions = new ArrayList<>();

//		MissionsDto language1 = new MissionsDto();
//		language1.setLabel("Java");
//		language1.setValue("Java");
//		languageOptions.add(language1);

//		MissionsDto language2 = new MissionsDto();  In case we need the controller for language selector.
//		language2.setLabel("Phyton");
//		language2.setValue("Phyton");
//		languageOptions.add(language2);
//
//		MissionsDto language3 = new MissionsDto();
//		language3.setLabel("C#");
//		language3.setValue("C#");
//		languageOptions.add(language3);
//
//		interfaceScreen.addAttribute("languageOptions", languageOptions);
		return "pages/user-home";
	}
}
