package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoApi = new ConsumoAPI();
	private final String URL_BASE = "https://www.omdbapi.com/?t=";//como es una constante el nombre va asi en mayus y separado por un raya al piso(NOMBRE_APELLIDO)
	private final String API_KEY = "&apikey=290c0af4";
	private ConvierteDatos conversor = new ConvierteDatos();

	public void muestraElMenu(){

		System.out.print("Por favor escribe el nombre de la serie que desea buscar: ");
		//Busca los datos de las series generales
		var nombreSerie = teclado.nextLine();
		var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
		System.out.println(json);
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		//Busca los datos de todas las temporadas
		List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
			json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+")+ "&Season="+i+API_KEY);
			var datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporada);
		}
		/*temporadas.forEach(System.out::println);*//*En el caso de System.out::println, le estamos diciendo al método forEach que, para cada elemento de la lista,
												ejecute el método println de la clase System.out para imprimir el elemento en la consola*/

		//Mostrar solo el titulo de los episodios para las temporadas
		/*for (int i = 0; i <datos.totalDeTemporadas() ; i++) {
			List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();*//*temporadas.get(i): Esta parte del código se utiliza para obtener el objeto que se encuentra en la posición en la colección de objetos*//*
			for (int j = 0; j <episodiosTemporada.size() ; j++) {
				System.out.println(episodiosTemporada.get(j).titulo());
			}
		}*/

		temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); /*esto es lo mismo que el codigo que esta arriaba (lambada) */

		//convertir todas las informaciones a una lista del tipo DatosEpisodio
		List<DatosEpisodio> datosEpisodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

		//top 5 Episodios
		System.out.println("Top 5 episodios");
		datosEpisodios.stream()
						.filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
						.sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
						.limit(5)
						.forEach(System.out::println);

		//Convirtiendo los datos a una lista del tipo Episodio
		List<Episodio> episodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d)))
				.collect(Collectors.toList());

		episodios.forEach(System.out::println);

		//Busqueda de episodios aparir de x año
		System.out.println("indica el año apartir del cual deseas ver los episodios");
		var fecha = teclado.nextLine();
		teclado.nextLine();

		LocalDate fehcaBusqueda = LocalDate.of(Integer.parseInt(fecha), 1,1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		episodios.stream()
				.filter(e -> e.getFechaDelazamiento() != null && e.getFechaDelazamiento().isAfter(fehcaBusqueda))
				.forEach(e -> System.out.println(
						"Temporada" + e.getTemporada() +
								"Episodio" + e.getTitulo() +
									"fecha de lanzamiento" + e.getFechaDelazamiento().format(dtf)
				));
	}

}
