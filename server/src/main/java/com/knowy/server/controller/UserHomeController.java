package com.knowy.server.controller;

import com.knowy.server.controller.model.MissionsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeController {

	@GetMapping("/home")
	public String userHome(Model interfaceScreen) {
		List<MissionsDto> missionsList = new ArrayList<>();
		MissionsDto mission1 = new MissionsDto();
		mission1.setName("Completa 3 lecciones");
		mission1.setCurrentProgress(2);
		mission1.setTotalProgress(3);
		mission1.setFractionProgress();
		missionsList.add(mission1);
		MissionsDto mission2 = new MissionsDto();
		mission2.setName("Completa 4 lecciones");
		mission2.setCurrentProgress(2);
		mission2.setTotalProgress(4);
		mission2.setFractionProgress();
		missionsList.add(mission2);
		MissionsDto mission3 = new MissionsDto();
		mission3.setName("Completa 5 lecciones");
		mission3.setCurrentProgress(2);
		mission3.setTotalProgress(5);
		mission3.setFractionProgress();
		missionsList.add(mission3);

		interfaceScreen.addAttribute("missionsList", missionsList);

		List<MissionsDto> languageOptions = new ArrayList<>();

		MissionsDto language1 = new MissionsDto();
		language1.setLabel("Java");
		language1.setValue("Java");
		languageOptions.add(language1);

		MissionsDto language2 = new MissionsDto();
		language2.setLabel("Phyton");
		language2.setValue("Phyton");
		languageOptions.add(language2);

		MissionsDto language3 = new MissionsDto();
		language3.setLabel("C#");
		language3.setValue("C#");
		languageOptions.add(language3);

		interfaceScreen.addAttribute("languageOptions", languageOptions);

		return "pages/user-home";
	}
}
