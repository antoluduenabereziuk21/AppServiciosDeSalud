package com.GrupoD.AppServSalud.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.GrupoD.AppServSalud.dominio.servicios.MailService;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Controller

public class MailController {

    @Autowired
    private MailService mailService;


    @GetMapping("/recuperarCuenta")
    public String recuperarCuenta(String email,ModelMap modelo) {
        
        try {
            mailService.sendMail(email);
            mailService.sendMailCalificacion(email);
            modelo.put("exito", "Se ha enviado un correo a su casilla de email");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
        }

        return "login.html";
    }

    @GetMapping("/recuperar")
    public String recuperar(String token, ModelMap modelo) {
        if (mailService.validateToken(token)) {
            modelo.put("token", token);
            return "forms/recuperarCuenta.html";
        } else {
            modelo.put("error", "El token no es valido");
            return "login.html";
        }
    }

    @PostMapping("/recuperarCuenta")
    public String recuperarCuenta(String token, String password,String password2, ModelMap modelo) {
        try {
            mailService.restoreAccount(token, password,password2);
            modelo.put("exito", "Se ha cambiado la contraseña con exito");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
        }
        return "login.html";
    }
    
}
