package com.GrupoD.AppServSalud.controlador;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.servicios.AdminServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServicio adminServicio;

    @GetMapping("/perfil/{email}")
    public String perfil(@PathVariable String email, ModelMap modelo, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelo.addAttribute("usuario", adminServicio.buscarPorEmail(usuario.getEmail()));
        return "vistaPerfil.html";
    }

}
