package com.aluracursos.screenmatch.model;

public enum Categoria {
	ACCCION ("Action", "Accion"),
	ROMANCE ("Romance", "Romance"),
	COMEDIA ("Comedy", "Comedia"),
	DRAMA ("Drama", "Drama"),
	CRIMEN ("Crime", "Crime");

	private String categoriaOmdb;
	private String categoriaEspanol;

	Categoria (String categoriaOmdb, String categoriaEspanol){//esto me ayuda a que las constantes no me arrojen error y sepuedan usar
		this.categoriaOmdb = categoriaOmdb;
		this.categoriaEspanol = categoriaEspanol;
	}

	public static Categoria fromString (String text){

		for(Categoria categoria : Categoria.values()){/*Tremos los valores, si la categoria coinsiden con las categorias mapaeadas y si no arroja el excepcion  */
			if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
				return categoria;
			}
		}
		throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
	}

	public static Categoria fromEspanol (String text){

		for(Categoria categoria : Categoria.values()){/*Tremos los valores, si la categoria coinsiden con las categorias mapaeadas y si no arroja el excepcion  */
			if (categoria.categoriaEspanol.equalsIgnoreCase(text)){
				return categoria;
			}
		}
		throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
	}
}
