package com.GrupoD.AppServSalud;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@SpringBootTest
class AppServSaludApplicationTests {

	@Autowired
	private OfertaServicio ofertaServicio;

	@Test
	void contextLoads() {
	}

}
