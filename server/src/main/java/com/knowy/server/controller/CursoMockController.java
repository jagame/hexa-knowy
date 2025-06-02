package com.knowy.server.controller;

import com.knowy.server.controller.model.CursoDTO;
import com.knowy.server.controller.model.LeccionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CursoMockController {

	@GetMapping("/test/curso-aside")
	public String mostrarCurso(Model model) {
		CursoDTO curso = new CursoDTO();
		curso.setNombre("Curso de Java Básico");
		curso.setPorcentajeCompletado(60);

		List<LeccionDTO> lecciones = List.of(
			new LeccionDTO(1, "Introducción", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(2, "Variables y Tipos", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(3, "Condicionales", LeccionDTO.EstadoLeccion.SIGUIENTE),
			new LeccionDTO(4, "Bucles", LeccionDTO.EstadoLeccion.BLOQUEADA)
		);

		curso.setLecciones(lecciones);
		model.addAttribute("curso", curso);
		return "pages/curso-aside-mock";
	}
}