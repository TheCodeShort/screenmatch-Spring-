package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table (name = "episodio")
public class Episodio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  long id;

	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodeo;
	private Double evaluacion;
	private LocalDate fechaDelazamiento;

	@ManyToOne //de muchos a 1 esto hace que se relacione episodios con serie
	private Serie serie;

	public Episodio(){}//constructor personalizado



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

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
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
