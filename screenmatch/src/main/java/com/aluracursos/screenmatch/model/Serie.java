package com.aluracursos.screenmatch.model;



import java.util.OptionalDouble;

public class Serie {
	 private String titulo;
	 private Integer totalDeTemporadas;
	 private Double evaluacion;
	 private Categoria  genero;
	private String poster;
	private String actores;

	public Serie(DatosSerie datosSerie) {
		this.titulo = datosSerie.titulo();
		this.poster = datosSerie.poster();
		this.actores = datosSerie.actores();
		this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
		this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
		this.totalDeTemporadas = datosSerie.totalDeTemporadas();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getTotalDeTemporadas() {
		return totalDeTemporadas;
	}

	public void setTotalDeTemporadas(Integer totalDeTemporadas) {
		this.totalDeTemporadas = totalDeTemporadas;
	}

	public Double getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Double evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Categoria getGenero() {
		return genero;
	}

	public void setGenero(Categoria genero) {
		this.genero = genero;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getActores() {
		return actores;
	}

	public void setActores(String actores) {
		this.actores = actores;
	}

	@Override
	public String toString() {
		return
				"titulo='" + titulo +
				", totalDeTemporadas=" + totalDeTemporadas +
				", evaluacion=" + evaluacion +
				", genero=" + genero +
				", poster='" + poster +
				", actores='" + actores
				;
	}
}
