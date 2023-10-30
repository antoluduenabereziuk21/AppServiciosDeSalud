package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

  @Autowired
  ServicioPaciente servicioPaciente;

  @GetMapping("/registro")
  public String registroPaciente(){
    return "forms/formularioPaciente.html";
  }
  
  @PostMapping("/registro")
  public String registroPaciente(String nombre, String apellido, String dni, String email,
                              String password, String sexo, String telefono, String obraSocial,
                              String fechaNacimiento){
    try {
      Date fechaNac =new Date(Integer.parseInt(fechaNacimiento.split("-")[0]) ,
                              Integer.parseInt(fechaNacimiento.split("-")[1]) ,
                              Integer.parseInt(fechaNacimiento.split("-")[2]) );
      servicioPaciente.crearPaciente(email, password, nombre, apellido,
                              dni, fechaNac, sexo, telefono,
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
