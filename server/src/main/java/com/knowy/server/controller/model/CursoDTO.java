package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CursoDTO {
	public CursoDTO() {}
	private String nombre;
	private int porcentajeCompletado;
	private List<LeccionDTO> lecciones;
}
