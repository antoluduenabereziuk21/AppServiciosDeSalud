package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;



@Slf4j
@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    private ServicioPaciente servicioPaciente;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/perfil/{email}")
    public String perfil(ModelMap modelo ,@PathVariable String email){
        Paciente paciente = servicioPaciente.buscarPorEmail(email);
        modelo.put("usuario", paciente);
        return "vistaPerfil.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/modificar/{email}")
    public String vistaModificarPaciente(@PathVariable String email, ModelMap modelo, HttpSession session){
     Paciente paciente = servicioPaciente.buscarPorEmail(email);
      modelo.put("paciente", paciente);
      return "forms/editarPaciente.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @PostMapping("/modificar/{email}")
    public String modificarPaciente(MultipartFile archivo, @PathVariable String email,
          @RequestParam String nombre, @RequestParam String apellido, @RequestParam String password,
          @RequestParam  String sexo, @RequestParam String telefono, @RequestParam String obraSocial,
                                  ModelMap modelo ,HttpSession session){
    //       String idHistoriaClinica, String idProfesional, String idTurno,
      Usuario usuario = (Usuario) session.getAttribute("usuario");
      if(!passwordEncoder.matches(password, usuario.getPassword())){
          modelo.put("error", "La contraseña ingresada no es correcta");
          modelo.put("paciente", servicioPaciente.buscarPorEmail(email));
          return "forms/editarPaciente.html";
      }
    try {
      servicioPaciente.modificarPaciente(archivo, email, nombre, apellido, sexo, telefono, obraSocial);//              idHistoriaClinica, idProfesional, idTurno);
      modelo.put("exito", "Paciente modificado correctamente");
       return "index.html";
    } catch (MiExcepcion e) {
      Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
      modelo.put("error", e.getMessage());
      Paciente paciente = (Paciente) session.getAttribute("usuario");
      modelo.put("paciente", paciente);
      return "forms/editarPaciente.html";
    }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{idPaciente}")
    public String eliminarPaciente(boolean enable, String idPaciente){
    servicioPaciente.bajaPaciente(enable, idPaciente);
    return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/todos")
    public String listarPacientes(ModelMap modelo){
    modelo.put("pacientesActivos", servicioPaciente.listarPacientes(true));
    modelo.put("pacientesInactivos", servicioPaciente.listarPacientes(false));
    modelo.put("paciente", new Paciente());
    return "pacientes.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/baja")
    public String bajaPaciente(String idPaciente){
        servicioPaciente.bajaPaciente(false, idPaciente);
        return "redirect:/paciente/todos";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/alta")
    public String altaPaciente(String idPaciente){
        servicioPaciente.bajaPaciente(true, idPaciente);
        return "redirect:/paciente/todos";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filtrarPacientes")
    public String buscarPorNombreYApellido(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,@RequestParam("email") String email, @RequestParam("dni") String dni, ModelMap modelo){
        modelo.put("pacientesActivos", servicioPaciente.filtrarPacientes(new FiltroUsuario(nombre, apellido, dni, email, true)));
        modelo.put("pacientesInactivos", servicioPaciente.filtrarPacientes(new FiltroUsuario(nombre, apellido, dni, email, false)));
        return "pacientes.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @PostMapping("/cambiarContrasenha")
    public String cambiarContrasenha(@RequestParam("password") String password,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("newPassword2") String newPassword2,
                                     ModelMap modelo, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelo.put("paciente", servicioPaciente.buscarPorEmail(usuario.getEmail()));
        if(!passwordEncoder.matches(password, usuario.getPassword())){
            modelo.put("error", "La contraseña ingresada no es correcta");
            return "forms/editarPaciente.html";
        }
        if(!newPassword.equals(newPassword2)){
            modelo.put("error", "Las contraseñas no coinciden");
            return "forms/editarPaciente.html";
        }
        servicioPaciente.cambiarContrasenha(usuario.getEmail(), newPassword);
        modelo.put("exito", "Contraseña modificada correctamente");

        return "forms/editarPaciente.html";
    }

    @GetMapping("/detalles/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerDetallesPaciente(@PathVariable String id) {
    // Lógica para obtener detalles del paciente con el ID proporcionado
    // Puedes devolver los detalles como un objeto JSON
    Paciente paciente = servicioPaciente.findById(id);
    // Ejemplo de respuesta con un objeto JSON
        Map<String, Object> detalles = new HashMap<>();
        detalles.put("id", id);
        detalles.put("paciente", paciente);
    // Agrega más detalles según sea necesario

    return ResponseEntity.ok(detalles);
  }

}
