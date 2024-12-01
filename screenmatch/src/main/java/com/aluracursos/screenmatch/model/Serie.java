package com.aluracursos.screenmatch.model;



import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
@Entity//de esta manera creamos una entidad
@Table(name = "series")//creamos una tabla y la podemos nombrar
public class Serie {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private  long id;
	 @Column(unique = true)
	 private String titulo;
	 private Integer totalDeTemporadas;
	 private Double evaluacion;
	 @Enumerated(EnumType.STRING)
	 private Categoria  genero;
	 private String poster;
	 private String actores;
	 private String sinopsis;

	 @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL,fetch = FetchType.EAGER) //de 1 a muchos esto hace que se relacionen las tablas  y las esta mapeando
	 private List<Episodio> episodios;

	 public Serie(){} //constructor predeterminado(personalizado) y somos obligado a agregarlos manualmente es una exigencia de que JPA

	public Serie(DatosSerie datosSerie) {
		this.titulo = datosSerie.titulo();
		this.poster = datosSerie.poster();
		this.actores = datosSerie.actores();
		this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
		this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
		this.totalDeTemporadas = datosSerie.totalDeTemporadas();
		this.sinopsis = datosSerie.sinopsis();
	}

	public List<Episodio> getEpisodios() {
		return episodios;
	}

	public void setEpisodios(List<Episodio> episodios) {
		 episodios.forEach(e -> e.setSerie(this));
		this.episodios = episodios;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return  "genero=" + genero +
				", titulo='" + titulo +
				", totalDeTemporadas=" + totalDeTemporadas +
				", evaluacion=" + evaluacion +
				 ", poster=" + poster +
				", actores=" + actores+
		        ", sinopsis=" + sinopsis+
				"episodios=" + episodios;
	}
}
