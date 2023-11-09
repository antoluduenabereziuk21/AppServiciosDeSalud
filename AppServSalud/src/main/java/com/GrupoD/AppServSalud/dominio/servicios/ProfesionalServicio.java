package com.GrupoD.AppServSalud.dominio.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.GrupoD.AppServSalud.dominio.entidades.Imagen;
import com.GrupoD.AppServSalud.utilidades.*;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.UsuarioRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

@Service
public class ProfesionalServicio {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;
  
  @Autowired
  private ProfesionalRepositorio profesionalRepositorio;

  @Autowired
  private ImagenServicio imagenServicio;
  
  @Transactional
  public void crearProfesional(String nombre, String apellido, String dni,
      Date fechaDeNacimiento, String email,
      String sexo, String telefono, String password,
      String matriculaProfesional, String especialidad) throws MiExcepcion {

    if (usuarioRepositorio.buscarPorEmail(email).isPresent()) {
      throw new MiExcepcion("El email ya se encuentra registrado");
    }

    if(usuarioRepositorio.buscarPorDni(dni).isPresent()){
      throw new MiExcepcion("El DNI ya se encuentra registrado");
    }

    Validacion.validarStrings(nombre, apellido, dni, email, sexo, telefono, password, matriculaProfesional,
        especialidad);
    Validacion.validarDate(fechaDeNacimiento);

    Profesional profesional = new Profesional();

    profesional.setNombre(nombre);
    profesional.setApellido(apellido);
    profesional.setMatriculaProfesional(matriculaProfesional);
    profesional.setEspecialidad(EspecialidadEnum.valueOf(especialidad));
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
  public void modificarProfesional(MultipartFile archivo, String email, String nombre, 
                                    String apellido, String sexo, String telefono, String descripcion) throws MiExcepcion {
    
    //Validacion.validarStrings(nombre, apellido, email, sexo, telefono);

    Optional<Profesional> respuestaProfesional = profesionalRepositorio.buscarPorEmail(email);

    if (respuestaProfesional.isPresent()) {
      Profesional profesional = respuestaProfesional.get();
      
      profesional.setNombre(nombre);
      profesional.setApellido(apellido);
      profesional.setSexo(Sexo.valueOf(sexo));
      profesional.setTelefono(telefono);
      profesional.setDescripcion(descripcion);
      if (!archivo.isEmpty()){
                String idImagen = null;

                if (profesional.getImagen() != null) {

                    idImagen = profesional.getImagen().getId();
                }

                Imagen imagen = null;

                try {
                    imagen = imagenServicio.actualizar(archivo, idImagen);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                profesional.setImagen(imagen);
            }


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

  public List<Profesional> listarProfesionales(boolean activo) {
    return profesionalRepositorio.listarTodos(activo);
  }

  public Profesional buscarPorId(String idProfesional) {
    Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);
    if (respuestaProfesional.isPresent()) {
      return profesionalRepositorio.findById(idProfesional).get();
    }
    return null;
  }

  public Profesional buscarPorEmail(String email) {
    return profesionalRepositorio.buscarPorEmail(email).get();
  }

  public List<Profesional> filtrarUsuarios(FiltroUsuario usuario) {
    return profesionalRepositorio.buscarPorFiltro(usuario);
  }
  

}
