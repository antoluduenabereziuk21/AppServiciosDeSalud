package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.CalificacionServicio;
import com.GrupoD.AppServSalud.dominio.servicios.MailService;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enviar-calificacion")
public class CalificacionControlador {
    
    @Autowired
    private CalificacionServicio calificacionServicio;
    @Autowired
    private MailService mailService;

//    // @GetMapping("/calificar")
//    // public String calificar(){
//    //     //PONER NOMBRE CORRESPONDIENTE EN LA VISTA HTML
//    //     return "calificarProfesional.html";
//    // }

    @GetMapping("/calificar")
    public String calificar(String email,ModelMap modelo) {
        
        try {
            mailService.sendMailCalificacion(email);
            modelo.put("exito", "Se ha enviado un correo a su casilla de email");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            //PONER NOMBRE CORRESPONDIENTE EN LA VISTA HTML
            return "calificarProfesional.html";
        }

        return "login.html";
    }
    
    

    @PostMapping("/calificar")
    public String calificar(Integer valor,String idProfesional,String idPaciente, String comentario, ModelMap modelo){
    
        try {
            calificacionServicio.guardarCalificacion(valor, idProfesional, idPaciente, comentario);
            modelo.put("exito", "Gracias por su calificacion");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
        }
        return "login.html";
    }
}
