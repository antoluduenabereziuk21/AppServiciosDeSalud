/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    public String llenarOferta() {

        return "formOferta.html";
    }

    @PostMapping("/enviar-oferta")
    public String enviarOferta(String tipoConsulta, String detalleOferta, String fechaConsulta,
            String horarioOferta, String ubicacionOferta, Double precioOferta, String profesionalOferta,
            ModelMap modelo) {

        try {
            ofertaServicio.crearOferta(tipoConsulta, detalleOferta, fechaConsulta, horarioOferta, ubicacionOferta,
                    precioOferta, profesionalOferta);
            modelo.put("exito", "La solicitud fue cargada correctamente");
        } catch (MiExcepcion ex) {
            return "redirect:/profesional/dashboard?error="+URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8);
        }
        return "redirect:/profesional/dashboard?exito="+URLEncoder.encode("La solicitud fue cargada correctamente", StandardCharsets.UTF_8);
    }
}
