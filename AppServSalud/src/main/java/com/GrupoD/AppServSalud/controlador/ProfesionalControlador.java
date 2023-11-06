package com.GrupoD.AppServSalud.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/registro")
  public String registroProfesional() {
    return "forms/registroProfesional.html";
  }

  @PostMapping("/registro")
  public String registroProfesional(String nombre, String apellido, String dni,
                                  String fechaDeNacimiento, String email,
                                  String sexo, String telefono, String password, ModelMap modelo) {

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fechaNacimiento = null;
      try {
        fechaNacimiento = dateFormat.parse(fechaDeNacimiento);
      } catch (ParseException ex) {
        Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
      }
      profesionalServicio.crearProfesional(nombre, apellido, dni, fechaNacimiento, email, sexo, telefono, password);
      modelo.put("exito", "Usuario Profesional creado correctamente");

      return "dashboardProfesional.html";

    } catch (MiExcepcion e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      modelo.put("error", e.getMessage());

      return "forms/registroProfesional.html";
    }

  }

  @GetMapping("/modificar/{idProfesional}")
  public String modificarProfesional(@PathVariable String idProfesional, ModelMap modelo){
    Profesional profesional = profesionalServicio.buscarPorId(idProfesional);
    modelo.put("profesional", profesional);
    return "forms/editarProfesional.html";
  }
  
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

  @PostMapping("/eliminar/{idProfesional}")
  public String eliminarProfesional(boolean enable, String idProfesional){
    profesionalServicio.bajaProfesional(enable, idProfesional);
    return "redirect:/";
  }

}