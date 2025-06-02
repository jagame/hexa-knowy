package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class LeccionDTO {
	private int numero;
	private String nombre;
	private EstadoLeccion estado; //depende de ENUM EstadoLeccion

	public enum EstadoLeccion {
		COMPLETADA,
		SIGUIENTE,
		BLOQUEADA
	}
}


