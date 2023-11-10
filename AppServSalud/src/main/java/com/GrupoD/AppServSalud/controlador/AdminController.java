package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.PermisoRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

  @Autowired
  private PermisoRepositorio permisoRepositorio;

  @GetMapping("/perfil/{email}")
  public String perfil(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    modelo.addAttribute("usuario", adminServicio.buscarPorEmail(usuario.getEmail()));
    return "vistaPerfil.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/registro")
  public String registroAdmin(ModelMap modelo) {
    modelo.put("permisos", permisoRepositorio.findAll());
    return "forms/crearAdmin.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/registro")
  public String registroAdmin(String nombre, String apellido, String email,
      String sexo, String telefono, String password, String[] permisos, ModelMap modelo) {
    System.out.println(Arrays.toString(permisos));
    try {
      adminServicio.crearAdmin(email, password, nombre, apellido, sexo, permisos);
      modelo.put("exito", "Administrador creado correctamente");
      return "index.html";
    } catch (MiExcepcion e) {
      modelo.put("error", e.getMessage());
      return "forms/crearAdmin.html";
    }
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

  @PostMapping("/modPaciente/{emailPath}")
  public String modificarPacienteAdmin(@PathVariable String emailPath, String nombre,
                    String apellido, String dni, String sexo, String fechaNacimiento,
                    String telefono, String obraSocial,String email, ModelMap modelo) {
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateFecha = null;
    try {
      dateFecha = dateFormat.parse(fechaNacimiento);
    } catch (ParseException ex) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      pacienteServicio.modificarPacienteAdmin(emailPath,email, nombre, apellido,dni, sexo, telefono,dateFecha,obraSocial);
      modelo.put("exito", "Paciente modificado correctamente");
      return "redirect:/paciente/todos";
    } catch (MiExcepcion e) {
      modelo.put("error", e.getMessage());
      return "forms/editarPacienteAdmin.html";
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modProfesional/{email}")
  public String vistaModificarProfesional(@PathVariable String email, ModelMap modelo, HttpSession session) {
    Profesional profesional = profesionalServicio.buscarPorEmail(email);
    modelo.put("profesional", profesional);
    return "forms/editarProfesionalAdmin.html";
  }

}
