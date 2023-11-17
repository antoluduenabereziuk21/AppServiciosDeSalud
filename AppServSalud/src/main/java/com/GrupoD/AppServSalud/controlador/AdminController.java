package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.PermisoRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  private UsuarioServicio usuarioServicio;

  @Autowired
  private AdminServicio adminServicio;

  @Autowired
  private ServicioPaciente pacienteServicio;

  @Autowired
  private ProfesionalServicio profesionalServicio;

  @Autowired
  private PermisoRepositorio permisoRepositorio;

  @Autowired
  private ServicioPaciente servicioPaciente;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/registro")
  public String registroAdmin(ModelMap modelo) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    modelo.put("usuario", usuarioServicio.getUsuario(userDetails.getUsername()));
    modelo.put("permisos", permisoRepositorio.findAll());
    return "forms/crearAdmin.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/registro")
  public String registroAdmin(String nombre, String apellido, String email,
      String sexo, String telefono, String password, String[] permisos, ModelMap modelo) {
    System.out.println(Arrays.toString(permisos));
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    try {
      adminServicio.crearAdmin(email, password, nombre, apellido, sexo, permisos);
      modelo.put("usuario", usuario);
      modelo.put("exito", "Administrador creado correctamente");
      return "index.html";
    } catch (MiExcepcion e) {
      modelo.put("usuario", usuario);
      modelo.put("error", e.getMessage());
      return "forms/crearAdmin.html";
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modificar")
  public String vistaModificarAdmin(ModelMap modelo) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Admin admin = adminServicio.buscarPorEmail(userDetails.getUsername());
    modelo.put("admin", admin);
    modelo.put("usuario", admin);
    return "forms/editarAdmin.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/modificar/{emailPath}")
  public String modificarAdmin(@PathVariable String emailPath, String nombre, String apellido,
      String dni, String sexo, String email, String telefono, ModelMap modelo) {
    
    try {
      adminServicio.modificarAdmin(emailPath, email, nombre, apellido, dni, sexo, telefono);
      return "redirect:/perfil/" + email+"?exito=Administrador modificado correctamente";
    } catch (MiExcepcion e) {
      modelo.put("error", e.getMessage());
      modelo.put("admin", adminServicio.buscarPorEmail(emailPath));
      return "forms/editarAdmin.html";
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modPaciente/{email}")
  public String vistaModificarPaciente(@PathVariable String email, ModelMap modelo, HttpSession session) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    Paciente paciente = pacienteServicio.buscarPorEmail(email);
    modelo.put("paciente", paciente);
    modelo.put("usuario", usuario);
    return "forms/editarPacienteAdmin.html";
  }

  @PostMapping("/modPaciente/{emailPath}")
  public String modificarPacienteAdmin(@PathVariable String emailPath, String nombre,
      String apellido, String dni, String sexo, String fechaNacimiento,
      String telefono, String obraSocial, String email, String password,
      ModelMap modelo, HttpSession session) {

    // Usuario usuario = (Usuario) session.getAttribute("usuario");
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      modelo.put("pacientesActivos", servicioPaciente.listarPacientes(true));
      modelo.put("pacientesInactivos", servicioPaciente.listarPacientes(false));
      modelo.put("error", "La contraseña ingresada no es correcta");
      modelo.put("usuario", usuario);
      return "pacientes.html";
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateFecha = null;
    try {
      dateFecha = dateFormat.parse(fechaNacimiento);
    } catch (ParseException ex) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      pacienteServicio.modificarPacienteAdmin(emailPath, email, nombre, apellido, dni, sexo, telefono, dateFecha,
          obraSocial);
      modelo.put("exito", "Paciente modificado correctamente");
      modelo.put("pacientesActivos", servicioPaciente.listarPacientes(true));
      modelo.put("pacientesInactivos", servicioPaciente.listarPacientes(false));
      modelo.put("usuario", usuario);
      return "pacientes.html";
    } catch (MiExcepcion e) {
      modelo.put("usuario", usuario);
      modelo.put("error", e.getMessage());
      return "forms/editarPacienteAdmin.html";
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/modProfesional/{email}")
  public String vistaModificarProfesional(@PathVariable String email, ModelMap modelo, HttpSession session) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    Profesional profesional = profesionalServicio.buscarPorEmail(email);
    modelo.put("profesional", profesional);
    modelo.put("usuario", usuario);
    return "forms/editarProfesionalAdmin.html";
  }

  @PostMapping("/modProfesional/{emailPath}")
  public String modificarProfesionalAdmin(@PathVariable String emailPath, String nombre,
      String apellido, String dni, String sexo, String fechaNacimiento,
      String telefono, String matriculaProfesional, String email, String password,
      String especialidad, ModelMap modelo, HttpSession session) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());

    // Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      modelo.put("profesionalesActivos", profesionalServicio.listarProfesionales(true));
      modelo.put("profesionalesInactivos", profesionalServicio.listarProfesionales(false));
      modelo.put("error", "La contraseña ingresada no es correcta");
      modelo.put("usuario", usuario);
      return "profesionales.html";
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateFecha = null;
    try {
      dateFecha = dateFormat.parse(fechaNacimiento);
    } catch (ParseException ex) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
    }

    try {
      profesionalServicio.modificarProfesionalAdmin(emailPath, email, nombre, apellido, dni, sexo, telefono, dateFecha,
          matriculaProfesional, especialidad);
      modelo.put("exito", "Profesional modificado correctamente");
      modelo.put("profesionalesActivos", profesionalServicio.listarProfesionales(true));
      modelo.put("profesionalesInactivos", profesionalServicio.listarProfesionales(false));
      modelo.put("usuario", usuario);
      return "profesionales.html";
    } catch (MiExcepcion e) {
      modelo.put("error", e.getMessage());
      modelo.put("usuario", usuario);
      return "forms/editarProfesionalAdmin.html";
    }

  }

}
