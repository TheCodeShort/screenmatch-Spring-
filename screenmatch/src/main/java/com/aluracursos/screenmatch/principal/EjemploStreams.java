package com.aluracursos.screenmatch.principal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {

	public void nuestroMetodo(){
		List<String> nombre = Arrays.asList("ranndy", "pedro", "cosa");
		nombre.stream()
				.sorted()
				/*.limit(2)*/
				.filter(n -> n.startsWith("p"))//n toma el valor de cada elemento del flujo, Para n = "ana", la condición n.startsWith("p") devuelve false.
				.map(n -> n.toUpperCase())
				.forEach(System.out::println);
	}
}
