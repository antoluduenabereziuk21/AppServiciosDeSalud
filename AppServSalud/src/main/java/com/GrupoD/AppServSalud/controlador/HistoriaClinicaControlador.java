package com.GrupoD.AppServSalud.controlador;

import java.time.DayOfWeek;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.servicios.HistoriaClinicaServicio;

@Controller
@RequestMapping("/historiaClinica")
public class HistoriaClinicaControlador {
    
    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;

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
