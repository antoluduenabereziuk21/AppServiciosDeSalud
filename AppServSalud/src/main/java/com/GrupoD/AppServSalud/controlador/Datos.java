package com.GrupoD.AppServSalud.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.servicios.NotificacionServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@RestController
@RequestMapping("/datos")
public class Datos {
    
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @GetMapping("/listarPorEspecialidad")
    public List<Profesional> listarPorEspecialidad(@RequestParam(name = "especialidad", required = false) String especialidad) {
        return profesionalServicio.listarPorEspecialidad(especialidad);
    }

    @GetMapping("/ofertas")
    public List<Object[]> listarOfertas(@RequestParam("fecha") String fecha,
                                      @RequestParam("idProfesional") String idProfesional) {
        return profesionalServicio.devolverIdyHorariosDisponibles(fecha, idProfesional);
    }

    @GetMapping("/marcarLeida/{idNotificacion}")
    public ResponseEntity<String> marcarNotificacionComoLeida(@PathVariable String idNotificacion) {
        try {
            notificacionServicio.marcarNotificacionComoLeida(idNotificacion);
            return ResponseEntity.ok("Notificación marcada como leída correctamente.");
        } catch (MiExcepcion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
