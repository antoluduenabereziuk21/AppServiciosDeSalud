/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.RegistroConsultaServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/registroDeConsulta")
public class RegistroConsultaControlador {

    @Autowired
    private RegistroConsultaServicio registroConsultaServicio;
    
    @GetMapping("/registro")
    public String registro(){
    
        //PONER NOMBRE CORRESPONDIENTE
        return "registroDeConsulta.html";
    }
    
    @PostMapping("/cargarRegistro")
    public String cargarRegistro(String idProfesional, String dni, String detalleConsulta, ModelMap modelo ){
    
        try {
            registroConsultaServicio.crearRegistroDeConsulta(idProfesional, detalleConsulta, dni);
            modelo.put("exito", "El registro fue guardado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            //PONER NOMBRE CORRESPONDIENTE
            return "registroDeConsulta.html";
        }
        
        return "index";
    }
    
    
}
