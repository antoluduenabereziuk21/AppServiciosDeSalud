package com.GrupoD.AppServSalud.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.servicios.ServicioPaciente;
import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

  @Autowired
  PacienteRepositorio pacienteRepositorio;

  @GetMapping("/perfil/{id}")
  public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {
    Paciente paciente = pacienteRepositorio.getOne(id);
    byte[] imagen = paciente.getImagen().getContenido();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
  }
}