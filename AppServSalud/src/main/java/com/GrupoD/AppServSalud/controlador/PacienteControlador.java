package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    private ServicioPaciente servicioPaciente;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registroPaciente() {
        return "forms/registroPaciente.html";
    }

    @PostMapping("/registro")
    public String registroPaciente(String nombre, String apellido, String dni, String email,
            String password, String sexo, String telefono,
            String fechaNacimiento, ModelMap modelo) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFecha = null;
            try {
                dateFecha = dateFormat.parse(fechaNacimiento);
            } catch (ParseException ex) {
                Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            servicioPaciente.crearPaciente(email, password, nombre, apellido, dni, dateFecha, sexo, telefono);

            modelo.put("exito", "Usuario creado correctamente");
            //devolver los permisos del usuario creado para que al redireccionar ya esté logueado
            modelo.put("usuario", null);
            return "index.html";
        } catch (MiExcepcion e) {
            Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
            modelo.put("error", e.getMessage());
            modelo.put("usuario", null);
            return "forms/registroPaciente.html";
        }

    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/modificar")
    public String vistaModificarPaciente(ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Paciente paciente = servicioPaciente.buscarPorEmail(userDetails.getUsername());
        modelo.put("paciente", paciente);
        //lo ideal sería solo usar usuario, pero habría que refactorizar todas las vistas
        modelo.put("usuario", paciente);
        return "forms/editarPaciente.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @PostMapping("/modificar/{idPaciente}") // cambiar por id
    public String modificarPaciente(MultipartFile archivo, String idPaciente,
            @RequestParam String nombre, @RequestParam String apellido, @RequestParam String password,
            @RequestParam String sexo, @RequestParam String telefono, @RequestParam String obraSocial,
            ModelMap modelo, HttpSession session) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Paciente paciente = servicioPaciente.buscarPorEmail(userDetails.getUsername());
        if (!passwordEncoder.matches(password, paciente.getPassword())) {
            modelo.put("error", "La contraseña ingresada no es correcta");
            modelo.put("paciente", paciente);
            modelo.put("usuario", paciente);
            return "forms/editarPaciente.html";
        }
        try {
            servicioPaciente.modificarPaciente(archivo, idPaciente, nombre, apellido, sexo, telefono,
                    obraSocial);
            modelo.put("exito", "Paciente modificado correctamente");
            modelo.put("usuario", paciente);
            return "index.html";
        } catch (MiExcepcion e) {
            Logger.getLogger(PacienteControlador.class.getName()).log(Level.SEVERE, null, e);
            modelo.put("error", e.getMessage());
            modelo.put("paciente", paciente);
            modelo.put("usuario", paciente);
            return "forms/editarPaciente.html";
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/{idPaciente}")
    public String eliminarPaciente(boolean enable, String idPaciente) {
        servicioPaciente.bajaPaciente(enable, idPaciente);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/todos")
    public String listarPacientes(ModelMap modelo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioServicio.getUsuario(userDetails.getUsername());
        modelo.put("pacientesActivos", servicioPaciente.listarPacientes(true));
        modelo.put("pacientesInactivos", servicioPaciente.listarPacientes(false));
        modelo.put("paciente", new Paciente());
        modelo.put("usuario", usuario);
        return "pacientes.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/baja")
    public String bajaPaciente(String idPaciente) {
        servicioPaciente.bajaPaciente(false, idPaciente);
        return "redirect:/paciente/todos";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/alta")
    public String altaPaciente(String idPaciente) {
        servicioPaciente.bajaPaciente(true, idPaciente);
        return "redirect:/paciente/todos";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filtrarPacientes")
    public String buscarPorNombreYApellido(@RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido, @RequestParam("email") String email,
            @RequestParam("dni") String dni, ModelMap modelo) {
        modelo.put("pacientesActivos",
                servicioPaciente.filtrarPacientes(new FiltroUsuario(nombre, apellido, dni, email, true)));
        modelo.put("pacientesInactivos",
                servicioPaciente.filtrarPacientes(new FiltroUsuario(nombre, apellido, dni, email, false)));
        return "pacientes.html";
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @PostMapping("/cambiarContrasenha")
    public String cambiarContrasenha(@RequestParam("password") String password,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("newPassword2") String newPassword2,
            ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelo.put("paciente", servicioPaciente.buscarPorEmail(usuario.getEmail()));
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            modelo.put("error", "La contraseña ingresada no es correcta");
            return "forms/editarPaciente.html";
        }
        if (!newPassword.equals(newPassword2)) {
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
        Paciente paciente = servicioPaciente.findById(id);
        Map<String, Object> detalles = new HashMap<>();
        detalles.put("id", id);
        detalles.put("paciente", paciente);
        return ResponseEntity.ok(detalles);// aplicar DTO para no enviar la entidad completa al script
    }

}
