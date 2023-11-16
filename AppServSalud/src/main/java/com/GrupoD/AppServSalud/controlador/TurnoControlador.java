package com.GrupoD.AppServSalud.controlador;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GrupoD.AppServSalud.dominio.servicios.TurnoServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;
    
    
    @GetMapping("/solicitar-turno")
    public String solicitarTurno(){
    
        //USAR NOMBRE CORRESPONDIENTE
        return "formTurno.html";
    }
    
    @PostMapping("/guardar-turno")
    public String guardarTurno(Date fechaTurno, String idPaciente, String idProfesional, String idOferta,ModelMap modelo){
    
        return "index.html";
    }

    @GetMapping("/misTurnos/{email}")
    public String misTurnos(@PathVariable String email, ModelMap modelo){
        try {
            modelo.put("turnos", turnoServicio.listarTurnosPorPaciente(email));
        } catch (MiExcepcion e) {
            return "redirect:/?error="+URLEncoder.encode(e.getMessage());
        }
        return "turnosPaciente.html";
    }

    @GetMapping("/aceptarTurno/{idTurno}")
    public String aceptarTurno(@PathVariable String idTurno, ModelMap modelo){
        try {
            turnoServicio.aceptarTurno(idTurno);
        } catch (MiExcepcion e) {
            return "redirect:/profesional/dashboard?error="+URLEncoder.encode(e.getMessage());
        }
        return "redirect:/profesional/dashboard?exito="+URLEncoder.encode("Turno aceptado con exito");
    }
    
}
