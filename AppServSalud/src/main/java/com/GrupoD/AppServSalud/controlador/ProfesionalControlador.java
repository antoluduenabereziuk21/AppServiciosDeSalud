package com.GrupoD.AppServSalud.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.servicios.ProfesionalServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

  @Autowired
  ProfesionalServicio profesionalServicio;

  @GetMapping("/dashboard")
  public String homeProfesional() {
    return "dashboardProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/registro")
  public String registroProfesional() {
    return "forms/registroProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/registro")
  public String registroProfesional(String nombre, String apellido, String dni,
                                  String fechaDeNacimiento, String email,
                                  String sexo, String telefono, String password,
                                   String matriculaProfesional, String especialidad, ModelMap modelo) {

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento = null;
      try {
        fechaNacimiento = dateFormat.parse(fechaDeNacimiento);
      } catch (ParseException ex) {
        Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
      }
      profesionalServicio.crearProfesional(nombre, apellido, dni, fechaNacimiento, email, sexo, telefono, password, matriculaProfesional, especialidad);
      modelo.put("exito", "Usuario Profesional creado correctamente");

      return "dashboardProfesional.html";

    } catch (MiExcepcion e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      modelo.put("error", e.getMessage());

      return "forms/registroProfesional.html";
    }

  }

  @PreAuthorize("hasRole('ROLE_MEDICO')")
  @GetMapping("/perfil/{email}")
  public String perfil(ModelMap modelo ,@PathVariable String email){
    Profesional profesional = profesionalServicio.buscarPorEmail(email);
    modelo.put("usuario", profesional);
    return "vistaPerfil.html";
  }

  @PreAuthorize("hasRole('ROLE_MEDICO', 'ROLE_ADMIN')")
  @GetMapping("/modificar/{idProfesional}")
  public String modificarProfesional(@PathVariable String idProfesional, ModelMap modelo){
    Profesional profesional = profesionalServicio.buscarPorId(idProfesional);
    modelo.put("profesional", profesional);
    return "forms/editarProfesional.html";
  }

  @PreAuthorize("hasRole('ROLE_MEDICO', 'ROLE_ADMIN')")
  @PostMapping("/modificar/{idProfesional}")
  public String modificarProfesional(MultipartFile archivo, @PathVariable String idProfesional, String nombre, 
                                    String apellido, String dni, Date fechaDeNacimiento, String email, 
                                    String sexo, String telefono, String password){
    try {
      profesionalServicio.modificarProfesional(archivo, idProfesional, nombre, apellido, dni, fechaDeNacimiento, email, sexo, telefono, password);
    } catch (MiExcepcion e) {
      Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, e);
      return "forms/editarProfesional.html";
    }
    return "redirect:/profesional/dashboard";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/eliminar/{idProfesional}")
  public String eliminarProfesional(boolean enable, String idProfesional){
    profesionalServicio.bajaProfesional(enable, idProfesional);
    return "redirect:/";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/todos")
  public String listarPacientes(ModelMap modelo){
    modelo.put("profesionalesActivos", profesionalServicio.listarProfesionales(true));
    modelo.put("profesionalesInactivos", profesionalServicio.listarProfesionales(false));
    return "profesionales.html";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/baja")
  public String bajaPaciente(String idProfesional){
    profesionalServicio.bajaProfesional(false, idProfesional);
    return "redirect:/profesional/todos";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/alta")
  public String altaPaciente(String idProfesional){
      profesionalServicio.bajaProfesional(true, idProfesional);
      return "redirect:/profesional/todos";
  }

  @GetMapping("/filtrar")
    public String filtrarProfesionales(@RequestParam("nombre") String nombre, @RequestParam("apellido")
                    String apellido, @RequestParam("email") String email, @RequestParam("dni") String dni,
                    ModelMap modelo){
        modelo.put("profesionalesActivos", profesionalServicio.filtrarUsuarios(new FiltroUsuario(nombre, apellido, dni, email,true)));
        modelo.put("profesionalesInactivos", profesionalServicio.filtrarUsuarios(new FiltroUsuario(nombre, apellido, dni, email,false)));
        return "profesionales.html";
    }

}