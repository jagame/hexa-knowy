package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.Course;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.service.CourseService;
import com.knowy.server.application.service.model.UserSecurityDetails;
import com.knowy.server.infrastructure.controller.dto.CourseCardDTO;
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

	private static final String TOAST_MODEL_ATTRIBUTE = "toast";

	private final CourseService courseService;

	public CoursesStoreController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("")
	public String storeCourses(
		Model model,
		@RequestParam(name = "category", required = false) String category,
		@RequestParam(name = "order", required = false) String order,
		@RequestParam(name = "page", defaultValue = "1") int page,
		@AuthenticationPrincipal UserSecurityDetails userDetails
	) throws KnowyInconsistentDataException {

		List<Course> allCourses = courseService.findAllCourses();

		List<Integer> myCourseIds = courseService.findCoursesByUserId(userDetails.getUser().id())
			.stream()
			.map(Course::id)
			.toList();

		List<Course> availableCourses = allCourses.stream()
			.filter(course -> !myCourseIds.contains(course.id()))
			.toList();

		List<CourseCardDTO> storeCourses = availableCourses.stream()
			.map(course -> CourseCardDTO.fromDomain(
				course,
				courseService.getCourseProgress(userDetails.getUser().id(), course.id()),
				CourseCardDTO.ActionType.ACQUIRE)
			).toList();

		//Filters
		if (category != null && !category.isEmpty()) {
			storeCourses = storeCourses.stream()
				.filter(card -> card.categories() != null && card.categories().contains(category))
				.toList();
		}
		if (order != null) {
			switch (order) {
				case "alpha_desc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::name, String.CASE_INSENSITIVE_ORDER).reversed())
					.toList();
				case "date_asc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::id))
					.toList();
				case "date_desc" -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::id).reversed())
					.toList();
				default -> storeCourses = storeCourses.stream()
					.sorted(Comparator.comparing(CourseCardDTO::name, String.CASE_INSENSITIVE_ORDER))
					.toList();
			}
		}

		// PAGINACIÓN
		int pageSize = 8;
		int totalPages = (int) Math.ceil((double) storeCourses.size() / pageSize);
		if (totalPages == 0) totalPages = 1; // mínimo 1 página

		if (page < 1) page = 1;
		if (page > totalPages) page = totalPages;

		int fromIndex = (page - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, storeCourses.size());

		List<CourseCardDTO> paginatedStoreCourses = fromIndex >= storeCourses.size() ? List.of() : storeCourses.subList(fromIndex, toIndex);


		model.addAttribute("courses", paginatedStoreCourses);
		model.addAttribute("allLanguages", courseService.findAllLanguages());
		model.addAttribute("order", order);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("category", category);
		model.addAttribute("acquireAction", "/store/subscribe");
		return "pages/courses-store";
	}

	@PostMapping("/subscribe")
	public String subscribeToCourse(
		@RequestParam("courseId") Integer courseId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		RedirectAttributes attrs
	) throws KnowyInconsistentDataException {
		CourseController.handleCourseSubscription(courseId, userDetails, attrs, courseService, TOAST_MODEL_ATTRIBUTE);
		return "redirect:/store";
	}
}

