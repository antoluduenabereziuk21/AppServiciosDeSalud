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

    @GetMapping("/v1")
    public String v1() {
        ofertaServicio.eliminarTodos();
        return "todos fueron eliminados";
    }

    @GetMapping("/v2")
    public String v2(@RequestParam("rango") Integer rango,
            @RequestParam("fecha") String fecha,
            @RequestParam("desde") String desde,
            @RequestParam("hasta") String hasta,
            @RequestParam("idProfesional") String idProfesional,
            @RequestParam("detalle") String detalle) {
        try {
            Date fechaOerta = null;
        
        if (fecha != null && !fecha.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fechaOerta = dateFormat.parse(fecha);
            } catch (ParseException ex) {
            }
        }

            ofertaServicio.crearOfertaPorRango(
                    fechaOerta,
                    desde,
                    hasta,
                    rango,
                    "TELEMEDICINA",
                    detalle,
                    "En mi consultorio",
                    2500d,
                    idProfesional);
            return "ofertas creada";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/v3")
    public Page<Oferta> testPages(@RequestParam Integer page, @RequestParam Integer size,
            @RequestParam(required = false) String apellido, @RequestParam(required = false) String especialidad,
            @RequestParam(required = false) String fecha, @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta) throws MiExcepcion {
        Pageable pageable = PageRequest.of(page, size,
            Sort.by("fecha").ascending()
                .and(Sort.by("horario").ascending()));
        return ofertaServicio.listarOfertas(pageable, apellido, especialidad, fecha, desde, hasta);
    }

   // @GetMapping("/notificacion_leida")

}
