package com.GrupoD.AppServSalud.dominio.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.RolEnum;
import com.GrupoD.AppServSalud.utilidades.Sexo;
import com.GrupoD.AppServSalud.utilidades.Validacion;

@Service
public class ProfesionalServicio {
  @Autowired
  private ProfesionalRepositorio profesionalRepositorio;

  @Transactional
  public void crearProfesional(String nombre, String apellido, String dni,
                                Date fechaDeNacimiento, String email,
                                String sexo, String telefono, String password) throws MiExcepcion{

    Validacion.validarStrings(nombre, apellido, dni, email, sexo, telefono, password);
    Validacion.validarDate(fechaDeNacimiento);

    Profesional profesional = new Profesional();

    profesional.setNombre(nombre);
    profesional.setApellido(apellido);
    profesional.setDni(dni);
    profesional.setFechaNacimiento(fechaDeNacimiento);
    profesional.setFechaAlta(new Date());
    profesional.setEmail(email);
    profesional.setSexo(Sexo.valueOf(sexo));
    profesional.setTelefono(telefono);
    profesional.setPassword(new BCryptPasswordEncoder().encode(password));
    profesional.setActivo(true);
    profesional.setRol(RolEnum.MEDICO);

    profesionalRepositorio.save(profesional);
  }

  @Transactional
  public void modificarProfesional(MultipartFile archivo, String idProfesional, String nombre, 
                                    String apellido, String dni, Date fechaDeNacimiento, String email, 
                                    String sexo, String telefono, String password) throws MiExcepcion {
    
    Validacion.validarStrings(nombre, apellido, dni, email, sexo, telefono, password);
    Validacion.validarDate(fechaDeNacimiento);

    Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);

    if (respuestaProfesional.isPresent()) {
      Profesional profesional = respuestaProfesional.get();

      profesional.setNombre(nombre);
      profesional.setApellido(apellido);
      profesional.setDni(dni);
      profesional.setFechaNacimiento(fechaDeNacimiento);
      profesional.setFechaAlta(new Date());
      profesional.setEmail(email);
      profesional.setSexo(Sexo.valueOf(sexo));
      profesional.setTelefono(telefono);
      profesional.setPassword(new BCryptPasswordEncoder().encode(password));
      profesional.setActivo(true);
      profesional.setRol(RolEnum.MEDICO);

      profesionalRepositorio.save(profesional);
    }
  }

  @Transactional
  public void bajaProfesional(boolean enable, String idProfesional) {
    Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);

    if (respuestaProfesional.isPresent()) {
      Profesional profesional = respuestaProfesional.get();
      profesional.setActivo(enable);
      profesionalRepositorio.save(profesional);
    }
  }

  public List<Profesional> listarProfesionales() {
    return profesionalRepositorio.findAll();
  }

  public Profesional buscarPorId(String idProfesional){
    Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);
    if (respuestaProfesional.isPresent()) {
      return profesionalRepositorio.findById(idProfesional).get(); 
    }
    return null;
  }

}
