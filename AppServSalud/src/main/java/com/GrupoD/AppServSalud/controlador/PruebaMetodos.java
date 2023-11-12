package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/v1")
    public String v1() {
        ofertaServicio.eliminarTodos();
        return "todos fueron eliminados";
    }

    @GetMapping("/v2/{rango}")
    public String v2(@PathVariable Integer rango) {
        try {
            ofertaServicio.crearOfertaPorRango(
                    new Date(2023-1900,11,18),
                    "09",
                    "20",
                    rango,
                    "TELEMEDICINA",
                    "Control niño sano",
                    "Control embarazo",
                    2500d,
                    "d1e24573-55e5-4868-aa7d-9fcc8a580de2");
            return "ofertas creada";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/v3")
    public Page<Oferta> testPages(Pageable pageable) {
        pageable = Pageable.ofSize(12);
        return ofertaServicio.listarOfertas(pageable);
    }
}
