package com.knowy.server.controller;

import com.knowy.server.controller.dto.LessonPageDataDTO;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final CourseSubscriptionService courseService;

	public LessonController(CourseSubscriptionService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/{courseId}")
	public String showCourseIntro(@PathVariable Integer courseId, Model model) {
		Integer userId = getLoggedInUserId();

		LessonPageDataDTO data = courseService.getCourseOverviewWithLessons(userId, courseId);

		model.addAttribute("course", data.getCourse());
		model.addAttribute("lessons", data.getCourse().getLessons());
		model.addAttribute("lastLesson", data.getLastLesson());
		model.addAttribute("courseId", courseId);
		model.addAttribute("isIntro", true);

		return "pages/lesson-explanation";
	}

	@GetMapping("/{courseId}/lesson/{lessonId}")
	public String showLesson(@PathVariable Integer courseId,
							 @PathVariable Integer lessonId,
							 Model model) {
		Integer userId = getLoggedInUserId();

		LessonPageDataDTO data = courseService.getLessonViewData(userId, courseId, lessonId);

		model.addAttribute("course", data.getCourse());
		model.addAttribute("lesson", data.getLesson());
		model.addAttribute("lessonContent", data.getLessonContent());
		model.addAttribute("lastLesson", data.getLastLesson());
//		model.addAttribute("level", data.getLevel());
		model.addAttribute("courseId", courseId);
		model.addAttribute("isIntro", false);

		return "pages/lesson-explanation";
	}

	private Integer getLoggedInUserId() {
		UserSecurityDetails userDetails = (UserSecurityDetails)
			SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getPublicUser().getId();
	}
}