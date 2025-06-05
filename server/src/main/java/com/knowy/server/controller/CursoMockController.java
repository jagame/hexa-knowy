package com.knowy.server.controller;

import com.knowy.server.controller.model.CursoDTO;
import com.knowy.server.controller.model.LeccionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CursoMockController {

	@GetMapping("/course")
	public String mostrarCurso(Model model) {
		CursoDTO curso = new CursoDTO();
		curso.setNombre("Java Básico");
		curso.setPorcentajeCompletado(80);

		List<LeccionDTO> lecciones = List.of(
			new LeccionDTO(1, "Introducción", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(2, "¿Qué es Java y cómo funciona?", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(3, "Tipos de datos y variables", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(4, "Estructuras de control", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(5, "Bucles", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(6, "Métodos (funciones)", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(7, "Arreglos (arrays)", LeccionDTO.EstadoLeccion.SIGUIENTE),
			new LeccionDTO(8, "Introducción ", LeccionDTO.EstadoLeccion.BLOQUEADA)
		);

		curso.setLecciones(lecciones);
		model.addAttribute("curso", curso);
		return "/pages/course-page";
	}
}