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
	@Autowired
	private ISerieRepositorio repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Principal principal = new Principal();
		principal.muestraElMenu();*/

		Principal2 principal2 = new Principal2(repositorio);
		principal2.muestraElMenu();
		//:5432

		/*EjemploStreams ejemploStreams = new EjemploStreams();
		ejemploStreams.nuestroMetodo();*/


	}


}
