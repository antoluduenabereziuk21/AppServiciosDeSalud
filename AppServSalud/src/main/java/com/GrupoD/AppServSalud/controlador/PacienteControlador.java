package com.GrupoD.AppServSalud.controlador;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

  @Autowired
  ServicioPaciente servicioPaciente;

  @GetMapping("/registro")
  public String registroPaciente(){
    return "formularioPaciente.html";
  }
  
  @PostMapping("/registro")
  public String registroPaciente(String nombre, String apellido, String dni, String correo, 
                              String contrasenha, String sexo, String telefono, String obraSocial, 
                              Date fechaDeNacimiento){
    try {
      servicioPaciente.crearPaciente(correo, contrasenha, nombre, apellido,
                              dni, fechaDeNacimiento, sexo, telefono,
                              obraSocial);
    } catch (Exception e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      return "formularioPaciente.html";
    }
    return "redirect:/";
  }

  @GetMapping("/modificar/{idPaciente}")
  public String modificarPaciente(MultipartFile archivo, String idPaciente, String email, String contrasenha, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            String sexo, String telefono, String obraSocial, String idHistoriaClinica, String idProfesional, String idTurno, ModelMap modelo){
    try {
      servicioPaciente.modificarPaciente(archivo, idPaciente, email, contrasenha, nombre, apellido, dni,
       fechaDeNacimiento,sexo, telefono, obraSocial, idHistoriaClinica, idProfesional, idTurno);
    } catch (Exception e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      return "forms/editarPaciente.html";
    }
    return "redirect:/";
  }

  @PostMapping("/eliminar/{idPaciente}")
  public String eliminarPaciente(boolean enable, String idPaciente){
    servicioPaciente.bajaPaciente(enable, idPaciente);
    return "redirect:/";
  }
}
