package com.aluracursos.screenmatch.principal;


import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repositorio.ISerieRepositorio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal2 {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoApi = new ConsumoAPI();
	private final String URL_BASE = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=290c0af4";
	private ConvierteDatos conversor = new ConvierteDatos();
	private List<DatosSerie> datosSeries = new ArrayList<>();
	private ISerieRepositorio repositorio;
	private List<Serie> series;
	private Optional <Serie> serieBuscada;

	public Principal2(ISerieRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	public void muestraElMenu() {

		var opcion = -1;
		while (opcion != 0) {
			var menu = """
                    1 - Buscar series. 
                    2 - Buscar episodios.
                    3 - Mostrar series buscadas.
                    4 - Mostrar series por titulo.
                    5 - Top series. 
                    6 - Buscar sseries por categoria.
                    7 - filtrar series por temporada.
                    8 - Buscar episodio por titulo.
                    9 - Top 5 episodio por serie.
                                  
                    0 - Salir.
                    """;
			System.out.println(menu);
			opcion = teclado.nextInt();
			teclado.nextLine();

			switch (opcion) {
				case 1:
					buscarSerieWeb();
					break;
				case 2:
					buscarEpisodioPorSerie();
					break;
				case 3:
					mostrarSeriesBuscadas();
					break;
				case 4:
					buscarSeriePorTitulo();
					break;
				case 5:
				buscarTop5Series();
					break;
				case 6:
				buscarSeriePorCategoria();
					break;
				case 7:
				filtrarSeriesPorTemporadaYEvaluacion();
					break;
				case 8:
					buscarEpisodiosPorTitulo();
				case 9:
					buscarTop5Episodio();
					break;
				case 0:
					System.out.println("Cerrando la aplicación...");
					break;
				default:
					System.out.println("Opción inválida");
			}
		}

	}


	private DatosSerie getDatosSerie() {
		System.out.println("Escribe el nombre de la serie que deseas buscar");
		var nombreSerie = teclado.nextLine();
		var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
		System.out.println(json);
		DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
		return datos;
	}

	private void buscarEpisodioPorSerie() {
		//DatosSerie datosSerie = getDatosSerie();
		mostrarSeriesBuscadas();
		System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
		var nombreSerie = teclado.nextLine();

		Optional<Serie> serie = series.stream()
										.filter(s->s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
										.findFirst();
		if(serie.isPresent()){
			var serieEncontrada= serie.get();
			List<DatosTemporadas> temporadas = new ArrayList<>();


			for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
				var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
				DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
				temporadas.add(datosTemporada);
			}
			temporadas.forEach(System.out::println);
			List<Episodio> episodios = temporadas.stream()
												.flatMap(d-> d.episodios().stream()
														.map(e -> new Episodio(d.numero(),e) ))
												.collect(Collectors.toList());
			serieEncontrada.setEpisodios(episodios);
			repositorio.save(serieEncontrada);
		}

	}
	private void buscarSerieWeb() {
		DatosSerie datos = getDatosSerie();
		Serie serie = new Serie(datos);
		repositorio.save(serie);
		/*datosSeries.add(datos);*/
		System.out.println(datos);
	}
	private void mostrarSeriesBuscadas() {

		series = repositorio.findAll();


		series.stream()
				.sorted(Comparator.comparing(Serie::getGenero))
				.forEach(System.out::println);
	}

	private void buscarSeriePorTitulo() {
		System.out.println("Escribe el nombre de la serie que desea buscar");
		var nombreSerie = teclado.nextLine();
		 serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

		if (serieBuscada.isPresent()) {
			System.out.println("La serie buscada es: " + serieBuscada.get());
		}else {
			System.out.println("Serie no encontrada");
		}
	}

	private void buscarTop5Series() {
		List<Serie>topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
		topSeries.forEach(s-> System.out.println("Serie:" + s.getTitulo() + "Evaluacion:" + s.getEvaluacion()));
	}

	private void buscarSeriePorCategoria() {
		System.out.println("Escriba el genero/categoria de la serie que desea buscar");
		var genero = teclado.nextLine();
		var categoria = Categoria.fromEspanol(genero);
		List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
		System.out.println("La series de la categoria:" + genero);
		seriesPorCategoria.forEach(System.out::println);

	}

	public  void  filtrarSeriesPorTemporadaYEvaluacion (){
		System.out.println("¿filtrar series con cuantas temporadas:?");
		var totalTemporadas = teclado.nextLine();
		teclado.nextLine();
		System.out.println("¿Con evaluacion apartir de cual valor?");
		var evaluacion = teclado.next();
		List<Serie> filtroSeries = repositorio.filtrarSeriesPorTemporadaYEvaluacion(totalTemporadas,evaluacion);
		System.out.println("**series filtradas**");
		filtroSeries.forEach(s-> System.out.println(s.getTitulo() + " - evaluacion:" + s.getEvaluacion()));
	}
	private void buscarEpisodiosPorTitulo() {
		System.out.println("Escribe el nombre del episodio que quieres buscar:");
		var nombreEpisodio = teclado.nextLine();
		List<Episodio> episodioEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
		episodioEncontrados.forEach(e-> System.out.printf("Serie: %s Temporada %s Evaluacion %s\n", e.getSerie(),e.getTemporada(),e.getEvaluacion()));
	}

	private void buscarTop5Episodio() {
		buscarSeriePorTitulo();
		if(serieBuscada.isPresent()){
			Serie serie = serieBuscada.get();
			List<Episodio> topEpisodio = repositorio.top5episodio(serie);
			topEpisodio.forEach(e-> System.out.printf("Serie: %s Temporada %s Evaluacion %s\n", e.getSerie(),e.getTitulo(),e.getEvaluacion()));
		}
	}



}
