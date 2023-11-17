package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.RegistroConsultaServicio;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        try {
            registroConsultaServicio.crearRegistroDeConsulta(idProfesional, idPaciente, detalleConsulta);
            modelo.put("exito", "El registro fue guardado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", "No se pudo guardar el registro");
            return "registroDeConsulta.html";
        }
        
        modelo.put("usuario", usuario);
        return "redirect:/profesional/dashboard";
    }
    
    
}
