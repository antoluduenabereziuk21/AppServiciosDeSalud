package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oferta")
public class OfertaControlador {

    @Autowired
    private OfertaServicio ofertaServicio;

    @GetMapping("/listar")
    public String listarOfertas(@RequestParam Integer page, @RequestParam Integer size,
            @RequestParam(required = false) String apellido, @RequestParam(required = false) String especialidad,
            @RequestParam(required = false) String fecha, @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta, ModelMap modelo) throws MiExcepcion {
        Pageable pageable = PageRequest.of(page, size,
            Sort.by("fecha").ascending()
                .and(Sort.by("horario").ascending()));
        modelo.put("ofertas", ofertaServicio.listarOfertas(pageable, apellido, especialidad, fecha, desde, hasta));
        modelo.put("especialidades", ofertaServicio.listarEspecialidades());
        return "ofertas.html";
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
            return "redirect:/profesional/dashboard?error=" + URLEncoder.encode(ex.getMessage());
        }
        return "redirect:/profesional/dashboard?exito=" + URLEncoder.encode("La solicitud fue cargada correctamente");
    }

    @PostMapping("/reservar")
    public String reservarOferta(String idOferta, String idPaciente) {

        try {
            ofertaServicio.reservarOferta(idOferta, idPaciente);
        } catch (MiExcepcion ex) {
            return "redirect:/especialidades?error=" + URLEncoder.encode(ex.getMessage());
        }
        return "redirect:/especialidades?exito=" + URLEncoder.encode("La oferta fue reservada correctamente");
    }

}
