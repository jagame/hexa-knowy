package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseBannerDTO;
import com.knowy.server.controller.dto.MissionsDto;
import com.knowy.server.controller.dto.NewsHomeDto;
import com.knowy.server.entity.CourseEntity;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.UserHomeService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserHomeController {

	private final UserHomeService userHomeService;
	private final CourseSubscriptionService courseSubscriptionService;

	public UserHomeController(
		UserHomeService userHomeService,
		CourseSubscriptionService courseSubscriptionService) {
		this.userHomeService = userHomeService;
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("/home")
	public String userHome(Model model, @AuthenticationPrincipal UserSecurityDetails userDetails) {
		Integer userId = userDetails.getPublicUser().getId();
		long coursesCompleted = userHomeService.getCoursesCompleted(userId);
		long totalCourses = userHomeService.getTotalCourses(userId);
		long percent = userHomeService.getCoursesPercentage(userId);
		boolean hasCourses = totalCourses > 0;

		model.addAttribute("hasCourses", hasCourses);
		model.addAttribute("completedCourses", coursesCompleted);
		model.addAttribute("totalCourses", totalCourses);
		model.addAttribute("fractionProgress", percent);

		List<CourseEntity> recommended = courseSubscriptionService.getRecommendedCourses(userId);
		List<Integer> recommendedIds = recommended.stream()
			.map(CourseEntity::getId)
			.toList();

		List<CourseEntity> fillCourses = courseSubscriptionService.findAllCourses().stream()
			.filter(c -> !recommendedIds.contains(c.getId()))
			.sorted(Comparator.comparing(CourseEntity::getCreationDate).reversed())
			.toList();

		List<CourseEntity> carouselCourses = new ArrayList<>();
		carouselCourses.addAll(recommended);

		for (CourseEntity c : fillCourses) {
			if (carouselCourses.size() >= 4) break;
			carouselCourses.add(c);
		}

		List<CourseBannerDTO> banners = carouselCourses.stream()
			.limit(4)
			.map(CourseBannerDTO::fromEntity)
			.toList();


		model.addAttribute("newsHome", banners);
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
