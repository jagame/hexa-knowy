package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/my-courses")
public class CourseController {

	private final CourseSubscriptionService courseSubscriptionService;

	public CourseController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("")
	public String myCourses (Model model,
							 @RequestParam(required = false) String category,
							 @RequestParam(required = false) String order,
							 @AuthenticationPrincipal UserSecurityDetails userDetails) {
		List<CourseCardDTO> courses = courseSubscriptionService.getUserCourses(userDetails.getPublicUser().getId());

		//Filter by language (category)
		if(category != null && !category.isEmpty()){
			courses = courses.stream()
				.filter(c-> c.getLanguages() != null && c.getLanguages().contains(category))
				.toList();
		}

		//Order
		if(order !=null){
			switch (order){
				case "alpha_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getName, String.CASE_INSENSITIVE_ORDER))
					.toList();

				case "alpha_desc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getName, String.CASE_INSENSITIVE_ORDER).reversed())
					.toList();

				case "progress_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getProgress))
					.toList();

				case "progress_desc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getProgress).reversed())
					.toList();


				case "date_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getId))
					.toList();

				case "date_desc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getId).reversed())
					.toList();

				default ->
					courses = courses.stream()
						.sorted(Comparator.comparing(CourseCardDTO::getProgress).reversed())
						.toList();
			}
		}

		model.addAttribute("allLanguages", courseSubscriptionService.findAllLanguages());
		model.addAttribute("courses", courses);
		model.addAttribute("recommendations", courseSubscriptionService.getRecommendedCourses(userDetails.getPublicUser().getId()));
		model.addAttribute("order", order);
		model.addAttribute("category", category);
		model.addAttribute("acquireAction", "/my-courses/subscribe");
		return "pages/my-courses";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(
		@RequestParam Integer courseId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes attrs
	){
		if (!courseSubscriptionService.subscribeUserToCourse(userDetails.getPublicUser().getId(), courseId)) {
			attrs.addFlashAttribute("error", "Error al adquirir el curso");
			return "redirect:/my-courses";
		}
		attrs.addFlashAttribute("success", "¡Te has suscrito correctamente!");
		return "redirect:/my-courses";
	}

}
