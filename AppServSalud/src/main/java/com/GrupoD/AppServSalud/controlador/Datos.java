package com.GrupoD.AppServSalud.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;

@RestController
@RequestMapping("/datos")
public class Datos {
    
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/listarPorEspecialidad")
    public List<Profesional> listarPorEspecialidad(@RequestParam(name = "especialidad", required = false) String especialidad) {
        return profesionalServicio.listarPorEspecialidad(especialidad);
    }

    @GetMapping("/ofertas")
    public List<HorarioEnum> listarOfertas(@RequestParam("fecha") String fecha,
                                      @RequestParam("idProfesional") String idProfesional) {
        return profesionalServicio.devolverHorariosDisponibles(fecha, idProfesional);
    }
}
