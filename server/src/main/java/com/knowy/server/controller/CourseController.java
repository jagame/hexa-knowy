package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.controller.dto.ToastDto;
import com.knowy.server.controller.exception.KnowyCourseSubscriptionException;
import com.knowy.server.entity.CourseEntity;
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
							 @RequestParam(defaultValue = "1") int page,
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

		model.addAttribute("allLanguages", courseSubscriptionService.findAllLanguages());
		model.addAttribute("courses", paginatedCourses);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
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
		try{
			courseSubscriptionService.subscribeUserToCourse(userDetails.getPublicUser().getId(), courseId);
			attrs.addFlashAttribute("toasts", List.of(new ToastDto("Éxito", "¡Te has suscrito correctamente!", ToastDto.ToastType.SUCCESS)));
		} catch(KnowyCourseSubscriptionException e){
			attrs.addFlashAttribute("toasts", List.of(new ToastDto("Error", e.getMessage(), ToastDto.ToastType.ERROR)));
		} catch (Exception e) {
			e.printStackTrace();
			attrs.addFlashAttribute("toasts", List.of(new ToastDto("Error", "Ocurrió un error inesperado al suscribirte al curso.", ToastDto.ToastType.ERROR)));
		}
		return "redirect:/my-courses";
	}

}
