
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private OfertaServicio ofertaServicio;
    

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/error_403")
    public String error403() {
        return "error_403.html";
    }

    @GetMapping("/error_404")
    public String error404() {
        return "error_404.html";
    }

    @GetMapping("/infoTurnos")
    public String infoTurno() {
        return "infoTurnos.html";
    }

    @GetMapping("/especialidades")
    public String especialidades() {
        return "especialidades.html";
    }

    @GetMapping("/tarjetaProfesional/{especialidad}")
    public String tarjetaProfesional(@PathVariable String especialidad, ModelMap modelo) {
        String espProf = especialidad.toUpperCase();
        List<Profesional> profesionales = profesionalRepositorio
                .buscarPorEspecialidad(EspecialidadEnum.valueOf(espProf));
        modelo.addAttribute("profesionales", profesionales);
        modelo.addAttribute("ofertas", Arrays.asList(
            new Oferta("of",TipoConsultaEnum.PRESENCIAL, "Detalle consulta", HorarioEnum._10HS, "ubicacion", 5000d, profesionales.get(0)),
            new Oferta("of",TipoConsultaEnum.TELEMEDICINA, "Detalle consulta", HorarioEnum._10HS, "ubicacion", 5000d, profesionales.get(0))
        
        ));
        return "tarjetaProfesional.html";
    }
    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/tarjetaProfesional/oferta")
    public String turno( HttpSession session){
        session.getAttribute("usuario");
        return "turnoPaciente.html";
    }

}
