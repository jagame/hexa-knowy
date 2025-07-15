package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.controller.dto.ToastDto;
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
@RequestMapping("/store")
public class CoursesStoreController {
	private final CourseSubscriptionService courseSubscriptionService;

	public CoursesStoreController(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	@GetMapping("")
	public String storeCourses(Model model,
							   @RequestParam(required = false) String category,
							   @RequestParam(required = false) String order,
							   @AuthenticationPrincipal UserSecurityDetails userDetails) {

		List<CourseCardDTO> storeCourses = courseSubscriptionService.getRecommendedCourses(userDetails.getPublicUser().getId());
	    //Filters
		if (category != null && !category.isEmpty()) {
			storeCourses = storeCourses.stream()
				.filter(c -> c.getLanguages() != null && c.getLanguages().contains(category))
				.toList();
		}
		if (order != null) {
			switch (order) {
				case "alpha_asc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getName, String.CASE_INSENSITIVE_ORDER))
					.toList();
				case "alpha_desc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getName, String.CASE_INSENSITIVE_ORDER).reversed())
					.toList();
				case "date_asc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getId))
					.toList();
				case "date_desc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getId).reversed())
					.toList();
				default -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::getName, String.CASE_INSENSITIVE_ORDER))
					.toList();
			}
		}
		List<ToastDto> toastList = List.of(new ToastDto("warning", "Este es un toast de prueba", ToastDto.ToastType.INFO));
		System.out.println("ToastList en modelo: " + toastList);
		model.addAttribute("toasts", toastList);

		model.addAttribute("allLanguages", courseSubscriptionService.findAllLanguages());
		model.addAttribute("courses", storeCourses);
		model.addAttribute("order", order);
		model.addAttribute("category", category);
		model.addAttribute("acquireAction", "/store/subscribe");
		return "pages/courses-store";
	}



	@PostMapping("/subscribe")
		public String subscribeToCourse(@RequestParam Integer courseId,
										@AuthenticationPrincipal UserSecurityDetails userDetails,
										RedirectAttributes attrs) {
		if (!courseSubscriptionService.subscribeUserToCourse(userDetails.getPublicUser().getId(), courseId)) {
			attrs.addFlashAttribute("toasts", List.of(new ToastDto("error", "Error al adquirir el curso", ToastDto.ToastType.ERROR)));
			return "redirect:/store";
		}
		attrs.addFlashAttribute("toasts", List.of(new ToastDto("success", "¡Curso adquirido con éxito!", ToastDto.ToastType.SUCCESS)));
		return "redirect:/store";
		}
	}

