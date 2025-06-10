package com.knowy.server.controller;

import com.knowy.server.controller.model.CursoDTO;
import com.knowy.server.controller.model.LeccionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoursePage {

	@GetMapping("/course")
	public String mostrarCurso(Model model) {
		CursoDTO curso = new CursoDTO();
		curso.setNombre("Java Básico");
		curso.setPorcentajeCompletado(40);

		List<LeccionDTO> lecciones = List.of(
			new LeccionDTO(1, "Introducción", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(2, "¿Qué es Java y cómo funciona?", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(3, "Tipos de datos y variables", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(4, "Estructuras de control", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(5, "Bucles", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(6, "Métodos (funciones)", LeccionDTO.EstadoLeccion.COMPLETADA),
			new LeccionDTO(7, "Arreglos (arrays)", LeccionDTO.EstadoLeccion.SIGUIENTE),
			new LeccionDTO(8, "Introducción a la Programación Orientada a Objetos", LeccionDTO.EstadoLeccion.BLOQUEADA),
			new LeccionDTO(9, "Introducción a la Programación Orientada a Objetos", LeccionDTO.EstadoLeccion.BLOQUEADA),
			new LeccionDTO(10, "Introducción a la Programación Orientada a Objetos", LeccionDTO.EstadoLeccion.BLOQUEADA)
		);
		int lastLesson = -1;
		for(int i = 0; i < lecciones.size(); i++) {
			if(lecciones.get(i).getEstado() == LeccionDTO.EstadoLeccion.COMPLETADA) {
				lastLesson = i;
			}
		}
		curso.setLecciones(lecciones);
		model.addAttribute("curso", curso);
		model.addAttribute("lastLesson", lastLesson);
		return "/pages/course-page";
	}
}