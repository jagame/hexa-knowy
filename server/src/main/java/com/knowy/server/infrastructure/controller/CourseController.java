package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.service.CourseService;
import com.knowy.server.infrastructure.security.UserSecurityDetails;
import com.knowy.server.infrastructure.controller.dto.CourseCardDTO;
import com.knowy.server.infrastructure.controller.dto.ToastDto;
import com.knowy.server.infrastructure.controller.exception.KnowyCourseSubscriptionException;
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

	private static final String TOAST_MODEL_ATTRIBUTE = "toast";

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	static void handleCourseSubscription(
		@RequestParam("courseId") Integer courseId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes attrs,
		CourseService courseService,
		String toastModelAttribute
	) throws KnowyInconsistentDataException {
		try {
			courseService.subscribeUserToCourse(userDetails.getUser(), courseId);
			attrs.addFlashAttribute(toastModelAttribute, List.of(new ToastDto("Éxito",
				"¡Te has suscrito correctamente!", ToastDto.ToastType.SUCCESS)));
		} catch (KnowyCourseSubscriptionException e) {
			attrs.addFlashAttribute(toastModelAttribute, List.of(new ToastDto("Error", e.getMessage(), ToastDto.ToastType.ERROR)));
		}
	}

	@GetMapping("")
	public String myCourses(
		Model model,
		@RequestParam(name = "category", required = false) String category,
		@RequestParam(name = "order", required = false) String order,
		@RequestParam(name = "page", defaultValue = "1") int page,
		@AuthenticationPrincipal UserSecurityDetails userDetails
	) throws KnowyInconsistentDataException {
		List<CourseCardDTO> courses = courseService.getUserCourses(userDetails.getUser().id())
			.stream()
			.map(course -> CourseCardDTO.fromDomain(
				course,
				courseService.getCourseProgress(userDetails.getUser().id(), course.id()),
				CourseCardDTO.ActionType.START
			))
			.toList();

		//Filter by language (category)
		if (category != null && !category.isEmpty()) {
			courses = courses.stream()
				.filter(card -> card.categories() != null && card.categories().contains(category))
				.toList();
		}

		//Order
		if (order != null) {
			switch (order) {
				case "alpha_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::name, String.CASE_INSENSITIVE_ORDER))
					.toList();

				case "alpha_desc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::name, String.CASE_INSENSITIVE_ORDER).reversed())
					.toList();

				case "progress_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::progress))
					.toList();

				case "date_asc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::id))
					.toList();

				case "date_desc" -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::id).reversed())
					.toList();

				default -> courses = courses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::progress).reversed())
					.toList();
			}
		}

		List<CourseCardDTO> recommendations = courseService
			.getRecommendedCourses(userDetails.getUser().id())
			.stream()
			.map(course -> CourseCardDTO.fromDomain(
				course,
				courseService.getCourseProgress(userDetails.getUser().id(), course.id()),
				CourseCardDTO.ActionType.ACQUIRE
			)).toList();

		int pageSize = 9;

		// 3. CALCULAR TOTAL DE PÁGINAS
		int totalPages = (int) Math.ceil((double) courses.size() / pageSize);
		if (totalPages == 0) totalPages = 1;  // mínimo 1 página

		// 4. RANGO DE PAGE
		if (page < 1) page = 1;
		if (page > totalPages) page = totalPages;

		int fromIndex = (page - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, courses.size());

		// 5 PAGINACIÓN
		List<CourseCardDTO> paginatedCourses = fromIndex >= courses.size() ? List.of() : courses.subList(fromIndex, toIndex);

		model.addAttribute("allLanguages", courseService.findAllLanguages());
		model.addAttribute("courses", paginatedCourses);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("recommendations", recommendations);
		model.addAttribute("order", order);
		model.addAttribute("category", category);
		model.addAttribute("acquireAction", "/my-courses/subscribe");
		return "pages/my-courses";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(
		@RequestParam("courseId") Integer courseId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes attrs
	) throws KnowyInconsistentDataException {
		handleCourseSubscription(courseId, userDetails, attrs, courseService, TOAST_MODEL_ATTRIBUTE);
		return "redirect:/my-courses";
	}

}