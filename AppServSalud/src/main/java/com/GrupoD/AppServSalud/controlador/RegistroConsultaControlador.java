/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.RegistroConsultaServicio;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PostMapping("/cargarRegistro")
    public String cargarRegistro(String idProfesional, String idPaciente, String detalleConsulta, ModelMap modelo ){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        modelo.put("usuario", usuario);
        try {
            registroConsultaServicio.crearRegistroDeConsulta(idProfesional, idPaciente, detalleConsulta);
            modelo.put("exito", "El registro fue guardado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", "No se pudo guardar el registro");
            return "registroDeConsulta.html";
        }
        
        return "redirect:/profesional/dashboard";
    }
    
    
}
