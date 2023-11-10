package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminServicio adminServicio;

  @Autowired
  private ServicioPaciente pacienteServicio;

  @Autowired
  private ProfesionalServicio profesionalServicio;

  @GetMapping("/perfil/{email}")
  public String perfil(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    modelo.addAttribute("usuario", adminServicio.buscarPorEmail(usuario.getEmail()));
    return "vistaPerfil.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modificar/{email}")
  public String vistaModificarAdmin(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Admin admin = adminServicio.buscarPorEmail(email);
    modelo.put("admin", admin);
    return "forms/editarAdmin.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modPaciente/{email}")
  public String vistaModificarPaciente(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Paciente paciente = pacienteServicio.buscarPorEmail(email);
    modelo.put("paciente", paciente);
    return "forms/editarPacienteAdmin.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modProfesional/{email}")
  public String vistaModificarProfesional(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Profesional profesional = profesionalServicio.buscarPorEmail(email);
    modelo.put("profesional", profesional);
    return "forms/editarProfesionalAdmin.html";
  }

}
