package com.knowy.server.controller;

import com.knowy.server.dto.NewsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


public class StreakController {
    @GetMapping("/Streak")
    public String StreakCardTry(ModelMap interfaceScreen) { //estos atributos de controladore serían en la futura página de usuario
        interfaceScreen.addAttribute("nombreUsuario", "Ana Pérez");
        interfaceScreen.addAttribute("progresoPorcentaje", 60);//estos valores tomarían datos de
        interfaceScreen.addAttribute("cursosCompletados", 5);
        interfaceScreen.addAttribute("totalCursos", 10);
        interfaceScreen.addAttribute("buttonLink", "/");
        interfaceScreen.addAttribute("rachaActual", 12);
        return "pages/StreakCardTry";
    }

}
