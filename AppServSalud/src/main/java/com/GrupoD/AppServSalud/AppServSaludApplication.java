package com.GrupoD.AppServSalud;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class AppServSaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServSaludApplication.class, args);
	}

	@Autowired
	private ServicioPaciente servicioPaciente;

	@Autowired
	private UsuarioServicio usuarioServicio;


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

			usuarioServicio.createAdminUser(
					"admin@mail.com",
					"admin",
					"Admin",
					"User",
					"ADMIN");

			servicioPaciente.crearPaciente(
					"Mauricio@mail.com",
					"123456",
					"Mauricio",
					"Mauricio",
					"11111111",
					new Date(),
					"MASCULINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Ayelen@mail.com",
					"123456",
					"Ayelen",
					"Ayelen",
					"22222222",new Date(),
					"FEMENINO",
					"3333333");

			servicioPaciente.crearPaciente(
					"Maria@mail.com",
					"123456",
					"Maria",
					"Maria",
					"33333333",new Date(),
					"FEMENINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Atonio@mail.com",
					"123456",
					"Atonio",
					"Atonio",
					"44444444",new Date(),
					"MASCULINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Araceli@mail.com",
					"123456",
					"Araceli",
					"Araceli",
					"55555555",new Date(),
					"FEMENINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Lucas@mail.com",
					"123456",
					"Lucas",
					"Lucas",
					"66666666",new Date(),
					"MASCULINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Ramiro@mail.com",
					"123456",
					"Ramiro",
					"Ramiro",
					"77777777",new Date(),
					"MASCULINO",
					"123123");

			servicioPaciente.crearPaciente(
					"Leandro@mail.com",
					"123456",
					"Leandro",
					"Leandro",
					"88888888",new Date(),
					"MASCULINO",
					"123123");

		};
	}

}
