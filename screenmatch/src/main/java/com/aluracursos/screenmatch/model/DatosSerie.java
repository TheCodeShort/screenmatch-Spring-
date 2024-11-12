package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)//al llamar el json tiene mas informacion este @JsonIgnoreProperties no ayuda a ignorar esa informacion que no solicitamos y que solo se quede la que si llamamos

public record DatosSerie(@JsonAlias ("Title") String titulo,/*@JsonAlias ("") lee la informacion de nuestra API y lo convierte a un objeto en java todo esto para ponerle un alias*/
                         @JsonAlias ("totalSeasons") Integer totalDeTemporadas,
                         @JsonAlias ("imdbRating") String evaluacion) {  /*@JsonProperty leer y escribir enviar datos*/
}
