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

	public static Categoria fromString (String text){

		for(Categoria categoria : Categoria.values()){/*Tremos los valores, si la categoria coinsiden con las categorias mapaeadas y si no arroja el excepcion  */
			if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
				return categoria;
			}
		}
		throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
	}

}
