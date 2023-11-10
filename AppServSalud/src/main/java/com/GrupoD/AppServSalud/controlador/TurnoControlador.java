/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.TurnoServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author leand
 */
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
    
        try {
            turnoServicio.crearTurno(fechaTurno, idPaciente, idProfesional, idOferta);
            modelo.put("exito", "Su turno fue guardado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            //USAR NOMBRE CORRESPONDIENTE
            return "formTurno.html";    
        }
        return "index.html";
    }
    
}