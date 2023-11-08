package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class PruebaMetodos {

    @Autowired
    ServicioPaciente servicioPaciente;

    @GetMapping("/buscardni/{dni}")
    public Paciente buscarPorDni(@PathVariable String dni) {
        return servicioPaciente.buscarPorDni(dni);
    }
}
