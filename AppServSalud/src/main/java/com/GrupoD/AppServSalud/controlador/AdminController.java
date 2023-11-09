package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Permiso;
import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.PermisoRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.PermisosEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminServicio adminServicio;

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

  @GetMapping("/lista")
  public String listar(List<String> permisos, ModelMap modelo) {
    List<Permiso> permisosAdmin = permisos.stream().map(
        permiso -> permisoRepositorio.findByPermiso(PermisosEnum.valueOf(permiso)).get()).collect(Collectors.toList());
    modelo.addAttribute("permisos", permisosAdmin);
    return "forms/crearAdmin.html";

  }

}
