package com.GrupoD.AppServSalud.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historiaClinica")
public class HistoriaClinicaControlador {

    @GetMapping("/registro")
    public String vistaRegistroHistoria() {
        return "template.html";
    }

    // @PostMapping("/registro")
    // public String registroHistoria(String historia, Paciente paciente, List<Profesional> profesional, ModelMap modelo){
        
    //     try {
    //         historiaClinicaServicio.crearHistoriaClinica(historia, paciente, null);
    //     } catch (Exception e) {
    //         Logger.getLogger(HistoriaClinicaControlador.class.getName()).log(Level.SEVERE, null, e);
    //         modelo.put("error", e.getMessage());
    //     }
    // }
}
