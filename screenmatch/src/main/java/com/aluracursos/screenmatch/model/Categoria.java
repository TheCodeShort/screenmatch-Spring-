package com.aluracursos.screenmatch.model;

public enum Categoria {
	ACCCION ("Action"),
	ROMANCE ("Romance"),
	COMEDIA ("Comedy"),
	DRAMA ("Drama"),
	CRIMEN ("Crime");

	private String categoriaOmdb;

	Categoria (String categoriaOmdb){
		this.categoriaOmdb = categoriaOmdb;
	}

	private static Categoria fromString (String text){

		for(Categoria categoria : Categoria.values()){
			if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
				return categoria;
			}
		}
		throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
	}

}
