package com.GrupoD.AppServSalud;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class AppServSaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServSaludApplication.class, args);
	}

	@Autowired
	private ServicioPaciente servicioPaciente;

	/**
	 * Metodo que se ejecuta al iniciar la aplicacion
	 *
	 * Aqui podemos hacer que se ejecuten metodos de prueba al iniciar la aplicacion
	 *
	 * @return
	 */
	@Bean
	CommandLineRunner init(){
		return args -> {
			servicioPaciente.crearPaciente("prueba@mail.com","123456",
					"pueba","prueba",
					"12312312",new Date(),"MASCULINO",
					"123123","SwissMedical");
		};
	}

}
