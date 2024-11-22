package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
		/*System.out.println(json);*/
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		/*System.out.println(datos);*/

		//Busca los datos de todas las temporadas
		List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
			json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+")+ "&Season="+i+API_KEY);
			var datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporada);
		}
		/*temporadas.forEach(System.out::println);*//*En el caso de System.out::println, le estamos diciendo al método forEach que, para cada elemento de la lista,
												ejecute el método println de la clase System.out para imprimir cada elemento  que esta en la lista en la consola*/

		//Mostrar solo el titulo de los episodios para las temporadas
		/*for (int i = 0; i <datos.totalDeTemporadas() ; i++) {
			List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios(); *//*Esta parte del código se utiliza para obtener el objeto que se encuentra en la posición en la colección de objetos*//*
			for (int j = 0; j <episodiosTemporada.size() ; j++) {
				System.out.println(episodiosTemporada.get(j).titulo());
			}
		}*/

		/*temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));*/ /*esto es lo mismo que el codigo que esta arriaba (lambada) */

		//convertir todas las informaciones a una lista del tipo DatosEpisodio
		List<DatosEpisodio> datosEpisodios = temporadas.stream()
														.flatMap(t -> t.episodios().stream())
														.collect(Collectors.toList());//tambien podemos finalizar toList se crea una lista inmutable no se puede poner mas infofrmacion
																						//con collect(Collectors.toList()) si podemos agregar mas informacion

		//top 5 Episodios
		System.out.println("Top 5 episodios");
		datosEpisodios.stream()
						.filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))// este filtro está diciendo: "Mantén solo los episodios cuya evaluación sea diferente de 'NA'"
//						.peek(e -> System.out.println("Primer filtro " + e))/*nos ayuda a ver el paso a paso de lo que va haciendo mi .stream*/
						.sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
						/*.peek(e -> System.out.println("Segundo ordenacion (M>m): " + e))*/
						.map(e -> e.titulo().toUpperCase())
						/*.peek(e -> System.out.println("Tercer filtro Mayusculas (m>M): " + e))*/
						.limit(5)
						.forEach(System.out::println);

		//Convirtiendo los datos a una lista del tipo Episodio
		List<Episodio> episodios = temporadas.stream()
									.flatMap(t -> t.episodios().stream()
									.map(d -> new Episodio(t.numero(),d)))
									.collect(Collectors.toList());

		/*episodios.forEach(System.out::println);*/

		//Busqueda de episodios aparir de x año
		System.out.print("indica el año apartir del cual deseas ver los episodios: ");
		var fecha = teclado.nextLine();
		teclado.nextLine();
		LocalDate fehcaBusqueda = LocalDate.of(Integer.parseInt(fecha), 1,1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		episodios.stream()
				.filter(e -> e.getFechaDelazamiento() != null && e.getFechaDelazamiento().isAfter(fehcaBusqueda))
				/*.forEach(e -> System.out.println(
						"Temporada" + e.getTemporada() +
								"Episodio" + e.getTitulo() +
									"fecha de lanzamiento" + e.getFechaDelazamiento().format(dtf)
				))*/;

		//busca episodio por pedazo del ttulo
		System.out.print("Por favcor escriba el titulo del episodio que desea ver: ");
		var pedazoTitulo = teclado.nextLine();
		Optional<Episodio> episodioBuscado = episodios.stream()//el optional me dice que puede tener o no informacion
				.filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
				.findFirst();//esto seria la operacion final del stream, trae el primer resultado, sigue ejecutando asi haya informacion o no (optional)
		if (episodioBuscado.isPresent()){
			System.out.println("Episodio encontrado");
			System.out.println("Los datos son:" + episodioBuscado.get());
		}else{
			System.out.println("Episodio no encontrado");
		}

	   Map<Integer, Double> evaluacionPorTemporada =  episodios.stream()
																.filter(e-> e.getEvaluacion() > 0.0)
																.collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getEvaluacion)));
		System.out.println(evaluacionPorTemporada);

		DoubleSummaryStatistics est = episodios.stream()
												.filter(e-> e.getEvaluacion() > 0.0)
												.collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
		System.out.println("La media de las evaluaciones: " + est.getAverage());
		System.out.println("Episodio mejor evaluado :" +est.getMax());
		System.out.println("Episodio peor evaluado: " + est.getMin());

	}
}
