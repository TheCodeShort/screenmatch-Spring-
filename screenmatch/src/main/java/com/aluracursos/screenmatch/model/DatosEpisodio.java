package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//con esto hacemos que ignore los datos que no se llamaron
public record DatosEpisodio(@JsonAlias ("Title") String titulo,
                            @JsonAlias ("Episode") Integer numeroEpisodio,
                            @JsonAlias ("imdbRating") String evaluacion,
                            @JsonAlias ("Released") String fehaDeLanzamiento) {
}
