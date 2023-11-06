
package com.GrupoD.AppServSalud.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/error_403")
    public String error403(){
        return "error_403.html";
    }

    @GetMapping("/error_404")
    public String error404(){
        return "error_404.html";
    }

}
