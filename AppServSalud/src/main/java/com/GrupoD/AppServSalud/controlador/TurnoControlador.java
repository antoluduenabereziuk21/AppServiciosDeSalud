package com.GrupoD.AppServSalud.controlador;

import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.TurnoServicio;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/solicitar-turno")
    public String solicitarTurno() {

        // USAR NOMBRE CORRESPONDIENTE
        return "formTurno.html";
    }

    @PostMapping("/guardar-turno")
    public String guardarTurno(Date fechaTurno, String idPaciente, String idProfesional, String idOferta,
            ModelMap modelo) {

        return "index.html";
    }

    @GetMapping("/misTurnos")
    public String misTurnos(@RequestParam(name = "exito",required = false) String exito,
            @RequestParam(name = "error", required = false) String error ,
            @RequestParam(name = "estado", required = false) String estado,
            ModelMap modelo) {
        if (estado == null){
            estado = "EN_ESPERA";
        }
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
            modelo.put("usuario", usuario);
            modelo.put("exito",exito);
            modelo.put("error",error);
            modelo.put("turnos", turnoServicio.filtrarPorEstadoYPaciente(userDetails.getUsername(), estado));
            modelo.put("estado", estado);
        } catch (MiExcepcion e) {
            return "redirect:/?error=" + URLEncoder.encode(e.getMessage());
        }
        return "turnosPaciente.html";
    }

    @GetMapping("/aceptarTurno/{idTurno}")
    public String aceptarTurno(@PathVariable String idTurno, ModelMap modelo) {
        try {
            turnoServicio.aceptarTurno(idTurno);
        } catch (MiExcepcion e) {
            return "redirect:/profesional/dashboard?error=" + URLEncoder.encode(e.getMessage());
        }
        return "redirect:/profesional/dashboard?exito=" + URLEncoder.encode("Turno aceptado con exito");
    }

    @GetMapping("/cancelarTurno/{idTurno}")
    public String cancelarTurno(@PathVariable String idTurno, ModelMap modelo){
        try{
            turnoServicio.cancelarTurno(idTurno);
        }catch (MiExcepcion e){
            modelo.put("error","No se pudo Cancelar el turno");
            return "redirect:/turno/misTurnos?error=" + URLEncoder.encode(e.getMessage());
        }
        return "redirect:/turno/misTurnos?exito=" + URLEncoder.encode("Turno cancelado con exito");
    }

}
