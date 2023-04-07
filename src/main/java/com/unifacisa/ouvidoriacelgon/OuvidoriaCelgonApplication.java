package com.unifacisa.ouvidoriacelgon;

import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import com.unifacisa.ouvidoriacelgon.repositories.ProtestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OuvidoriaCelgonApplication implements CommandLineRunner {

	@Autowired
	private ProtestRepository protestRepository;

	public static void main(String[] args) {
		SpringApplication.run(OuvidoriaCelgonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
