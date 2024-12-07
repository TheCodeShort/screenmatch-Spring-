package com.aluracursos.screenmatch.model;



import jakarta.persistence.*;//JAP

import java.util.List;
import java.util.OptionalDouble;


@Entity//de esta manera creamos una entidad con JPA
@Table(name = "series")//creamos una tabla y la podemos nombrar
public class Serie {
	 @Id//aca decimos que este sera mi ID de la tabla
	 @GeneratedValue(strategy = GenerationType.IDENTITY)//estrategia de generacion de mi ID cual sera mi ID 1,2,3, una llave incremental
	 private  long id;//long nos indica que el ID sera un numero de 64 bits y el (id) es el nombre que le damos a la variable

	 @Column(unique = true)//con esto decimos que no queremos cosas repetidas en este caso q no se repia una serie
	 private String titulo;
	 private Integer totalDeTemporadas;
	 private Double evaluacion;
	 @Enumerated(EnumType.STRING)//para identificar un valor que es distinto en este caso esto biene de la clase ENUM asi que tenemos que especificarlo
	 private Categoria  genero;
	 private String poster;
	 private String actores;
	 private String sinopsis;

	 //@Transient conesto decimos que existe la lista episodio pero no la vamos a utilizar
	 @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL,fetch = FetchType.EAGER) //de 1 a muchos esto hace que se relacionen las tablas  y las esta mapeando
	 //traer los datos cuando lo pidamos o traerlo deuna vez con fetch = FetchType.EAGER
	 private List<Episodio> episodios;

	 public Serie(){} //constructor predeterminado(personalizado) y somos obligado por JPA a agregarlos manualmente es una exigencia de que JPA

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
				" episodios=" + episodios;
	}
}
