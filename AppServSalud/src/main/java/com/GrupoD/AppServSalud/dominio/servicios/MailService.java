package com.GrupoD.AppServSalud.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.UsuarioRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.JwtUtils;


@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void sendMail(String email) throws MiExcepcion {

        usuarioRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("anonimo@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Recuperacion de cuenta");
        String body = "Hola, \n\n" +
                "Hemos recibido una solicitud para recuperar tu cuenta. \n\n" +
                "Si no has sido tu, ignora este mensaje. \n\n" +
                "Si has sido tu, haz click en el siguiente enlace para recuperar tu cuenta: \n\n" +
                "http://localhost:8080/recuperar?token=" + jwtUtils.generateToken(email) + "\n\n" +
                "Saludos, \n\n" +
                "El equipo de AppServSalud";
        mailMessage.setText(body);

        mailSender.send(mailMessage);

    }

    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    public void restoreAccount(String token, String password,String password2) throws MiExcepcion {

        if(!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden");
        }

        if (!validateToken(token)) {
            throw new MiExcepcion("El token no es valido");
        }
        
        String email = jwtUtils.getEmail(token);

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));

        usuario.setPassword(passwordEncoder.encode(password));

        usuarioRepositorio.save(usuario);
    }
    

}
