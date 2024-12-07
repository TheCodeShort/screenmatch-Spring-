package com.aluracursos.screenmatch;


import com.aluracursos.screenmatch.principal.Principal;

import com.aluracursos.screenmatch.principal.Principal2;
import com.aluracursos.screenmatch.repositorio.ISerieRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Autowired//esto le indica a spring qu ese le va a hacer una inyeccion de dependencias
	private ISerieRepositorio repositorio;//aca indicamos que es lo que le vamos a inyectar par ausar los metodos
											//para usar los metodos toca crear un constructor

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Principal principal = new Principal();
		principal.muestraElMenu();*/

		Principal2 principal2 = new Principal2(repositorio);
		principal2.muestraElMenu();
		//:5432 puerto de posgres por defecto

		/*EjemploStreams ejemploStreams = new EjemploStreams();
		ejemploStreams.nuestroMetodo();*/


	}


}
