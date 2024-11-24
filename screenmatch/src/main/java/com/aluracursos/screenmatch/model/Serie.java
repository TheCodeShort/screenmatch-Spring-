package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {
	 private String titulo;
	 private Integer totalDeTemporadas;
	 private Double evaluacion;
	 private Categoria  genero;

	public Serie(DatosSerie datosSerie) {
		this.titulo = titulo;
		this.poster = poster;
		this.actores = actores;
		this.genero = genero;
		this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
		this.totalDeTemporadas = totalDeTemporadas;
	}

	private String actores;
	 private String poster;
}
