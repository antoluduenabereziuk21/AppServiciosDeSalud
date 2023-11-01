package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;

import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



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
                                 String password, String sexo, String telefono,
                                 String fechaNacimiento, ModelMap modelo){
                          // String password, String sexo, String telefono, String obraSocial,String fechaNacimiento, ModelMap modelo){
                              

    try {
    
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFecha = null;
        try {
            dateFecha = dateFormat.parse(fechaNacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

      servicioPaciente.crearPaciente(email,password,nombre,apellido,dni,dateFecha,sexo,telefono);
                    //dni, dateFecha, sexo, telefono,obraSocial);Se dejan atributos comentados para que no se rompa el codigo
                              
      modelo.put("exito", "Usuario creado correctamente");
      return "index.html";
    } catch (MiExcepcion e) {

      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      modelo.put("error", e.getMessage());
     
      return "forms/formularioPaciente.html";
    }
   
  }

  @GetMapping("/modificar/{idPaciente}")
  public String modificarPaciente(MultipartFile archivo, String idPaciente, String email, String contrasenha, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            String sexo, String telefono, String obraSocial, String idHistoriaClinica, String idProfesional, String idTurno, ModelMap modelo){
    try {
      servicioPaciente.modificarPaciente(archivo, idPaciente, email, contrasenha, nombre, apellido, dni,
       fechaDeNacimiento,sexo, telefono, obraSocial, idHistoriaClinica, idProfesional, idTurno);
    } catch (MiExcepcion e) {
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

  @GetMapping("/todos")
  public String listarPacientes(ModelMap modelo){
    modelo.put("pacientesActivos", servicioPaciente.listarPacientesActivos());
    modelo.put("pacientesInactivos", servicioPaciente.listarPacientesInactivos());
    modelo.put("paciente", new Paciente());
    return "pacientes.html";
  }

  @PostMapping("/baja")
    public String bajaPaciente(String idPaciente){
        servicioPaciente.bajaPaciente(false, idPaciente);
        return "redirect:/paciente/todos";
    }

    @PostMapping("/alta")
    public String altaPaciente(String idPaciente){
        servicioPaciente.bajaPaciente(true, idPaciente);
        return "redirect:/paciente/todos";
    }

    @GetMapping("/buscarPorNombreYApellido")
    public String buscarPorNombreYApellido(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, ModelMap modelo){
          try {
              modelo.put("pacientesActivos", servicioPaciente.filtrarActivo(nombre, apellido));
              modelo.put("pacientesInactivos", servicioPaciente.filtrarInactivo(nombre, apellido));
          }catch (MiExcepcion e){
              modelo.put("error", e.getMessage());
          }
        return "pacientes.html";
    }

}
