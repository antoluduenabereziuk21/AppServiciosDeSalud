/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
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
@RequestMapping("/oferta")
public class OfertaControlador {
    
    @Autowired
    private OfertaServicio ofertaServicio;
    
    @GetMapping("/llenar-oferta")
    public String llenarOferta(){
    
        //USAR NOMBRE CORRESPONDIENTE
        return "formOferta.html";
    }
    
    @PostMapping("/enviar-oferta")
    public String enviarOferta(TipoConsultaEnum tipo,String detalle,HorarioEnum horario,String ubicacion,
            Double precio, String idProfesional, ModelMap modelo){
    
        try {
            ofertaServicio.crearOferta(tipo, detalle, horario, ubicacion, precio, idProfesional);
            modelo.put("exito", "La solicitud fue cargada correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            //USAR NOMBRE CORRESPONDIENTE
            return "formOferta.html";
        }
        return "index.html";
    }
}
