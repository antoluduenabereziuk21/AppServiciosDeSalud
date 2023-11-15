package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class PruebaMetodos {

    @Autowired
    ServicioPaciente servicioPaciente;

    @Autowired
    OfertaServicio ofertaServicio;

    @GetMapping("/buscardni/{dni}")
    public Paciente buscarPorDni(@PathVariable String dni) {
        return servicioPaciente.buscarPorDni(dni);
    }
}
