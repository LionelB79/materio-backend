package com.materio.materio_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"com.materio.materio_backend",
	"com.squelette.squelette_backend"
})
public class MaterioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterioBackendApplication.class, args);
	}

}
