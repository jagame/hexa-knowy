package com.knowy.server.controller;

import com.knowy.server.controller.model.CourseCardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

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
}
