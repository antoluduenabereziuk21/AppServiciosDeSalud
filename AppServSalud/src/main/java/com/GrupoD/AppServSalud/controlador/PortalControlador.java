
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

    /*Este metodo solo es para controlar que spring security este configurado correctamente
    @GetMapping("/bienvenido")
    public String bienvenido(){
        return "bienvenido.html";
    }*/

}
