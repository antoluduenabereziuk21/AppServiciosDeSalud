package com.GrupoD.AppServSalud.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.OfertaServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.dominio.servicios.TurnoServicio;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

  @Autowired
  ProfesionalServicio profesionalServicio;

  @Autowired
  ServicioPaciente servicioPaciente;

  @Autowired
  private OfertaServicio ofertaServicio;

  @Autowired
  private TurnoServicio turnoServicio;

  @Autowired
  UsuarioServicio usuarioServicio;
  
  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @GetMapping("/dashboard")
  public String homeProfesional(@RequestParam(name = "exito", required = false) String exito,
      @RequestParam(name = "error", required = false) String error, ModelMap modelo) {

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Profesional profesional = profesionalServicio.buscarPorEmail(userDetails.getUsername());
    List<Oferta> ofertas = ofertaServicio.listarOfertasProfesional(profesional.getId());
    modelo.put("pacientesActivos", servicioPaciente.listarPacientes(true));
    modelo.put("pacientesInactivos", servicioPaciente.listarPacientes(false));
    modelo.put("profesional", profesional);// eliminar la variable de las vistas y usar usuario para unificar código
    modelo.put("usuario", profesional);
    modelo.put("ofertas", ofertas);
    modelo.put("turnos", turnoServicio.listarTurnosPorProfesional(profesional.getId()));
    modelo.put("exito", exito != null ? exito : null);
    modelo.put("error", error != null ? error : null);
    return "dashboardProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @PostMapping("/paciente/baja")
  public String bajaPaciente(String idPaciente) {
    servicioPaciente.bajaPaciente(false, idPaciente);
    return "redirect:/profesional/dashboard";
  }

  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @PostMapping("/paciente/alta")
  public String altaPaciente(String idPaciente) {
    servicioPaciente.bajaPaciente(true, idPaciente);
    return "redirect:/profesional/dashboard";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/registro")
  public String registroProfesional(ModelMap modelo) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    modelo.put("usuario", usuario);
    return "forms/registroProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/registro")
  public String registroProfesional(String nombre, String apellido, String dni,
      String fechaDeNacimiento, String email,
      String sexo, String telefono, String password,
      String matriculaProfesional, String especialidad, ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento = null;
      try {
        fechaNacimiento = dateFormat.parse(fechaDeNacimiento);
      } catch (ParseException ex) {
        Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
      }
      profesionalServicio.crearProfesional(nombre, apellido, dni, fechaNacimiento, email, sexo, telefono, password,
          matriculaProfesional, especialidad);
      modelo.put("exito", "Usuario Profesional creado correctamente");
      modelo.put("usuario", usuario);
      return "forms/registroProfesional.html";

    } catch (MiExcepcion e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      modelo.put("error", e.getMessage());
      modelo.put("usuario", usuario);
      return "forms/registroProfesional.html";
    }

  }

  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @GetMapping("/modificar")
  public String modificarProfesional(ModelMap modelo) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Profesional profesional = profesionalServicio.buscarPorEmail(userDetails.getUsername());
    modelo.put("profesional", profesional);
    modelo.put("usuario", profesional);
    return "forms/editarProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @PostMapping("/modificar/{email}")
  public String modificarProfesional(MultipartFile archivo, @PathVariable String email, String nombre,
      String apellido, String sexo, String telefono, String descripcion,
      ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profesional profesional = profesionalServicio.buscarPorEmail(userDetails.getUsername());
    try {
      profesionalServicio.modificarProfesional(archivo, email, nombre, apellido, sexo, telefono, descripcion);
      modelo.put("exito", "Datos modificados exitosamente");
      modelo.put("usuario", profesional);
      return "vistaPerfil.html";
    } catch (MiExcepcion e) {
      modelo.put("error", e.getMessage());
      modelo.put("profesional", profesional);
      modelo.put("usuario", profesional);
      return "forms/editarProfesional.html";
    }

  }

  @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_MEDICO')")
  @PostMapping("/eliminar/{idProfesional}")
  public String eliminarProfesional(boolean enable, String idProfesional) {
    profesionalServicio.bajaProfesional(enable, idProfesional);
    return "redirect:/";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/todos")
  public String listarProfesionales(ModelMap modelo) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
    modelo.put("profesionalesActivos", profesionalServicio.listarProfesionales(true));
    modelo.put("profesionalesInactivos", profesionalServicio.listarProfesionales(false));
    modelo.put("usuario", usuario);
    return "profesionales.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/baja")
  public String bajaProfesional(String idProfesional) {
    profesionalServicio.bajaProfesional(false, idProfesional);
    return "redirect:/profesional/todos";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/alta")
  public String altaProfesional(String idProfesional) {
    profesionalServicio.bajaProfesional(true, idProfesional);
    return "redirect:/profesional/todos";
  }

  @GetMapping("/darBaja/{iProfesional}")
  public String darseDeBaja(@PathVariable String iProfesional, ModelMap modelo, HttpServletRequest request,
      HttpServletResponse response) {
    profesionalServicio.bajaProfesional(false, iProfesional);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    modelo.put("exito", "Usuario dado de baja");
    return "login.html";
  }

  @GetMapping("/filtrar")
  public String filtrarProfesionales(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
      @RequestParam("email") String email, @RequestParam("dni") String dni,
      ModelMap modelo) {
    modelo.put("profesionalesActivos",
        profesionalServicio.filtrarUsuarios(new FiltroUsuario(nombre, apellido, dni, email, true)));
    modelo.put("profesionalesInactivos",
        profesionalServicio.filtrarUsuarios(new FiltroUsuario(nombre, apellido, dni, email, false)));
    return "profesionales.html";
  }

}