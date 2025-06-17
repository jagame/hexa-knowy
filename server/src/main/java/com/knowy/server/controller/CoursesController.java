package com.knowy.server.controller;

import com.knowy.server.controller.model.CourseCardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class CoursesController {

	//Test-page
	@GetMapping("/test")
	public String viewCoruseCard(ModelMap interfaceScreen) {
		CourseCardDTO courseTest1 = new CourseCardDTO();
		courseTest1.setName("Curso Java/Angular 2025");
		courseTest1.setCreator("Creador del curso");
		courseTest1.setProgress(55);
		if(courseTest1.getProgress()>0 && courseTest1.getProgress()<100){
			courseTest1.setAction("▷ Continuar curso");
		}else if(courseTest1.getProgress() == 0){
			courseTest1.setAction("▷ Empezar curso");
		}else{
			courseTest1.setAction("∞ Repetir curso");
		}
		courseTest1.setTags(new ArrayList<>(Arrays.asList("HTML", "JavaScript", "SQL")));
		interfaceScreen.addAttribute("course", courseTest1);
		return "pages/test";
	}

	@GetMapping("/my-courses")
	public String viewMyCourses(Model model) {

		//card-1 test
		CourseCardDTO courseTest1 = new CourseCardDTO();
		courseTest1.setName("Curso Java/Angular 2025");
		courseTest1.setCreator("Creador del curso");
		courseTest1.setProgress(55);
		if(courseTest1.getProgress()>0 && courseTest1.getProgress()<100){
			courseTest1.setAction("▷ Continuar curso");
		}else if(courseTest1.getProgress() == 0){
			courseTest1.setAction("▷ Empezar curso");
		}else{
			courseTest1.setAction("∞ Repetir curso");
		}
		courseTest1.setTags(new ArrayList<>(Arrays.asList("HTML", "JavaScript", "SQL")));

		//card-2 test
		CourseCardDTO courseTest2 = new CourseCardDTO();
		courseTest2.setName("Curso Java/Angular 2025");
		courseTest2.setCreator("Creador del curso");
		courseTest2.setProgress(55);
		if(courseTest2.getProgress()>0 && courseTest2.getProgress()<100){
			courseTest2.setAction("▷ Continuar curso");
		}else if(courseTest2.getProgress() == 0){
			courseTest2.setAction("▷ Empezar curso");
		}else{
			courseTest2.setAction("∞ Repetir curso");
		}
		courseTest2.setTags(new ArrayList<>(Arrays.asList("HTML", "JavaScript", "SQL")));

		//card-3 test
		CourseCardDTO courseTest3 = new CourseCardDTO();
		courseTest3.setName("Curso Python 2025");
		courseTest3.setCreator("Creador del curso");
		courseTest3.setProgress(55);
		if(courseTest3.getProgress()>0 && courseTest3.getProgress()<100){
			courseTest3.setAction("▷ Continuar curso");
		}else if(courseTest3.getProgress() == 0){
			courseTest3.setAction("▷ Empezar curso");
		}else{
			courseTest3.setAction("∞ Repetir curso");
		}
		courseTest3.setTags(new ArrayList<>(Arrays.asList("HTML", "JavaScript", "SQL")));

		List<CourseCardDTO> courses = Arrays.asList(courseTest1, courseTest2, courseTest3);
		model.addAttribute("courses", courses);
		return "pages/my-courses";
	}
}
