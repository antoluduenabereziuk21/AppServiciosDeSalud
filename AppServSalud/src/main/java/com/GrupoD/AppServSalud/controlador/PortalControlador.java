
package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.RolEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index(ModelMap modelo, HttpSession session) {
        // try {
        // UserDetails userDetails = (UserDetails)
        // SecurityContextHolder.getContext().getAuthentication()
        // .getPrincipal();
        // Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        // modelo.put("usuario", usuario);
        // return "index.html";
        // } catch (Exception e) {
        // modelo.put("usuario", null);
        // return "index.html";
        // }
        Usuario usuario = null;
        if (session.getAttribute("usuario") != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            usuario = usuarioServicio.getUsuario(userDetails.getUsername());

            int cantidadNotificaciones = usuario.getNotificaciones().size();
            System.out.println("estoy recibiendo las notificaciones :" + cantidadNotificaciones);
            modelo.put("cantidadNotificaciones", cantidadNotificaciones);
        }
        modelo.put("usuario", usuario);
        return "index.html";

    }

    @GetMapping("/login")
    public String login(ModelMap modelo, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/"; // Redirigir a la página principal si ya ha iniciado sesión
        }
        modelo.put("usuario", null);
        return "login.html";
    }

    @GetMapping("/validar-usuario")
    public String redireccionLogin(ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        modelo.put("usuario", usuario);
        if (usuario.getRol().equals(RolEnum.PACIENTE)) {
            return "redirect:/especialidades";
        }
        if (usuario.getRol().equals(RolEnum.MEDICO)) {
            return "redirect:/profesional/dashboard";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEDICO') or hasRole('ROLE_PACIENTE')")
    @GetMapping("/perfil")
    public String perfil(@RequestParam(name = "exito", required = false) String exito, ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        modelo.put("usuario", usuario);
        modelo.put("exito", exito);
        return "vistaPerfil.html";
    }

    @GetMapping("/error_403")
    public String error403(ModelMap modelo) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
            modelo.put("usuario", usuario);
            return "error_403.html";
        } catch (Exception e) {
            modelo.put("usuario", null);
            return "error_403.html";
        }
    }

    @GetMapping("/error_404")
    public String error404(ModelMap modelo) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
            modelo.put("usuario", usuario);
            return "error_404.html";
        } catch (Exception e) {
            modelo.put("usuario", null);
            return "error_404.html";
        }
    }

    @GetMapping("/infoTurnos")
    public String infoTurno(ModelMap modelo) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
            modelo.put("usuario", usuario);
            return "infoTurnos.html";
        } catch (Exception e) {
            modelo.put("usuario", null);
            return "infoTurnos.html";
        }

    }

    @GetMapping("/especialidades")
    public String especialidades(@RequestParam(required = false) String error,
            @RequestParam(required = false) String exito, ModelMap modelo) {
        Usuario usuario = null;
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            usuario = usuarioServicio.getUsuario(userDetails.getUsername());
            modelo.put("usuario", usuario);
            modelo.put("especialidades",EspecialidadEnum.values());
        } catch (Exception e) {
        }
        if (error != null) {
            modelo.addAttribute("error", error);
        }
        if (exito != null) {
            modelo.addAttribute("exito", exito);
        }
        modelo.put("usuario", usuario);
        return "especialidades.html";
    }

    @GetMapping("/tarjetaProfesional/{especialidad}")
    public String tarjetaProfesional(@PathVariable String especialidad, ModelMap modelo, HttpSession session) {
        modelo.put("especialidad", especialidad);
        String espProf = especialidad.toUpperCase();
        List<Profesional> profesionales = profesionalRepositorio
                .buscarPorEspecialidad(EspecialidadEnum.valueOf(espProf));
        modelo.addAttribute("profesionales", profesionales);
        Usuario usuario = null;
        if (session.getAttribute("usuario") != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            usuario = usuarioServicio.getUsuario(userDetails.getUsername());
           
        }
         modelo.put("usuario", usuario);
            return "tarjetaProfesional.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/tarjetaProfesional/oferta")
    public String turno(ModelMap modelo, HttpSession session) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        session.getAttribute("usuario");
        modelo.put("usuario", usuario);
        return "turnoPaciente.html";
    }

}
