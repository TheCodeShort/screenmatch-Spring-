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
				.filter(n -> n.startsWith("p"))
				.map(n -> n.toUpperCase())
				.forEach(System.out::println);
	}
}
