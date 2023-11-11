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

    public void sendMailCalificacion(String email) throws MiExcepcion {

        usuarioRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("anonimo@gmail.com");
        mailMessage.setTo(email);
      
        mailMessage.setSubject("Calificacion del profesional");
        String body = "Encuesta, \n\n" +
                "Encuesta de safisfacion y mejora contunia. \n\n" +
                "Estimado paciente, mediante las siguiente encuesta queremos conocer su valoracion acerca de nuestro servicios. \n\n" +
                "Sus respuestas nos van a permitir analizar acciones en pos de mejorar su satisfacion. \n\n"+
                "Haz click en el siguiente enlace para calificar al profesional: \n\n" +
                "http://localhost:8080/calificar="+ jwtUtils.generateTokenCalificacion(email)+ "\n\n" +
                "Saludos, \n\n" +
                "El equipo de AppServSalud";
        mailMessage.setText(body);

        mailSender.send(mailMessage);

    }
    
  public void notificarPacienteTurno(String email,boolean aceptado, String emailProfesional) throws MiExcepcion{
        usuarioRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));
        
        Usuario profesional = usuarioRepositorio.buscarPorEmail(emailProfesional)
                .orElseThrow(() -> new MiExcepcion("No existe un Profesional con ese email"));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom("anonimo@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject(aceptado ? "Turno aceptado" : "Turno rechazado");
        String body = "Hola, \n\n" +
                "Se le notifica que su turno con el profesional " + profesional.getNombre() + " " + profesional.getApellido() + " \n\n" +
                "ha sido " + (aceptado ? "aceptado" : "rechazado") + ". \n\n" +
                "Saludos, \n\n" +
                "El equipo de AppServSalud";
        mailMessage.setText(body);

        mailSender.send(mailMessage);
    }
  

    public void notificarProfesionalTurno(String email, String emailPaciente) throws MiExcepcion{
        usuarioRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));
        Usuario usuario = usuarioRepositorio.buscarPorEmail(emailPaciente)
                .orElseThrow(() -> new MiExcepcion("No existe un usuario con ese email"));

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("anonimo@gmail.com");
        mailMessage.setTo(email);

        mailMessage.setSubject("Notificacion de turno");
        String body = "Hola, \n\n" +
                "Se le notifica que tiene una solicitud de turno. \n\n" +
                "Usuario: " + usuario.getNombre() + " " + usuario.getApellido() + "\n\n" +
                "DNI: " + usuario.getDni() + "\n\n" +
                "Email: " + usuario.getEmail() + "\n\n" +
                "Recuerde que tiene que aprobar o rechazar el turno"+ "\n\n" +
                "Saludos, \n\n" +
                "El equipo de AppServSalud";
        mailMessage.setText(body);

        mailSender.send(mailMessage);

    }
}
