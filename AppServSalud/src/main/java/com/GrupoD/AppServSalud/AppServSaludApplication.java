package com.GrupoD.AppServSalud;

import com.GrupoD.AppServSalud.dominio.entidades.Permiso;
import com.GrupoD.AppServSalud.dominio.repositorio.PermisoRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.utilidades.PermisosEnum;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class AppServSaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServSaludApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private ServicioPaciente servicioPaciente;

	@Autowired
	private AdminServicio adminServicio;

	@Autowired
	private ProfesionalServicio profesionalServicio;

	@Autowired
	private PermisoRepositorio permisoRepositorio;


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

			Arrays.stream(PermisosEnum.values()).forEach(permisoEnum -> {
				if (!permisoRepositorio.findByPermiso(permisoEnum).isPresent()) {
					Permiso permiso = Permiso.builder().permiso(permisoEnum).build();
					permisoRepositorio.save(permiso);
				}
			});
			
			log.info("Permisos guardados en la base de datos");

			profesionalServicio.crearProfesional(
					"medico",
					"ginecologo",
					"12333212",
					new Date(),
					"ginecologo@mail.com",
					"X",
					"12333322",
					"123456",
					"MP-122211",
					"GINECOLOGIA"
			);

			log.info("profesional guardado en la base de datos");


			adminServicio.crearAdmin(
					"superadmin@admin.com",
					"123456", 
					"Super ADMIN",
					"Ranger", 
					"ADMIN",
					Arrays.asList(
						"ADD_PROFESIONAL",
						"EDIT_PROFESIONAL",
						"DELETE_PROFESIONAL",
						"ADD_TURN",
						"EDIT_TURN",
						"DELETE_TURN",
						"ADD_MEDIC",
						"EDIT_MEDIC",
						"DELETE_MEDIC",
						"ADD_ADMIN",
						"EDIT_ADMIN",
						"DELETE_ADMIN",
						"EDIT_PERMISIONS"
					));

			log.info("admin guardado en la base de datos");

			servicioPaciente.crearPaciente(
					"mauricio1990arg@gmail.com",
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
					"Antonio@mail.com",
					"123456",
					"Antonio",
					"Antonio",
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
