package com.aluracursos.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {

	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodeo;
	private Double evaluacion;
	private LocalDate fechaDelazamiento;



	public Episodio(Integer numero, DatosEpisodio d) {
		this.temporada = numero;
		this.titulo = d.titulo();
		this.numeroEpisodeo = d.numeroEpisodio();
		try {
			this.evaluacion = Double.valueOf(d.evaluacion());

		}catch (NumberFormatException e){
			this.evaluacion = 0.0;
		}

		try {
			this.fechaDelazamiento = LocalDate.parse(d.fehaDeLanzamiento());

		}catch (DateTimeException e){
			this.fechaDelazamiento = null;
		}



	}

	public Integer getTemporada() {
		return temporada;
	}

	public void setTemporada(Integer temporada) {
		this.temporada = temporada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getNumeroEpisodeo() {
		return numeroEpisodeo;
	}

	public void setNumeroEpisodeo(Integer numeroEpisodeo) {
		this.numeroEpisodeo = numeroEpisodeo;
	}

	public LocalDate getFechaDelazamiento() {
		return fechaDelazamiento;
	}

	public void setFechaDelazamiento(LocalDate fechaDelazamiento) {
		this.fechaDelazamiento = fechaDelazamiento;
	}

	public Double getEvaluacion() {
		return evaluacion;
	}

	@Override
	public String toString() {
		return
				"temporada = " + temporada +
				", titulo = " + titulo +
				", numeroEpisodeo = " + numeroEpisodeo +
				", evaluacion = " + evaluacion +
				", fechaDelazamiento = " + fechaDelazamiento;
	}
}
