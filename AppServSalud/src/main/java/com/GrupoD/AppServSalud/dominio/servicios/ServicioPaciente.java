package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Imagen;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.dominio.repositorio.HistoriaClinicaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.TurnoRepositorio;
import com.GrupoD.AppServSalud.excepciones.Excepcion;
import com.GrupoD.AppServSalud.utilidades.ObraSocial;
import com.GrupoD.AppServSalud.utilidades.Rol;
import com.GrupoD.AppServSalud.utilidades.Sexo;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class ServicioPaciente {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private ImagenServicio imagenServio;
    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Transactional
    public void crearPaciente(MultipartFile archivo, String email, String contraseña, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            Sexo sexo, String telefono, ObraSocial obraSocial, String idHistoriaClinica, String idProfesional, String idTurno) throws Excepcion {

        validar(email, contraseña, nombre, apellido, dni, fechaDeNacimiento, sexo, telefono, obraSocial, idProfesional, idTurno);
        
        HistoriaClinica historiaClinica = historiaClinicaRepositorio.findById(idHistoriaClinica).get();
        Profesional profesional = profesionalRepositorio.findById(idProfesional).get();
        Turno turno = turnoRepositorio.findById(idTurno).get();

        Paciente paciente = new Paciente();

        paciente.setEmail(email);
        paciente.setContraseña(contraseña);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaDeNacimiento(fechaDeNacimiento);
        paciente.setTelefono(telefono);
        paciente.setEnable(true);
        paciente.setFechaCreacion(new Date());
        paciente.setRol(Rol.PACIENTE);
        paciente.setSexo(sexo);
        paciente.setObraSocial(obraSocial);
        paciente.setHistoriaClinica(historiaClinica);
        paciente.setProfesional(profesional);
        paciente.setTurno(turno);

        Imagen imagen = imagenServio.Guardar(archivo);

        paciente.setImagen(imagen);

        pacienteRepositorio.save(paciente);
    }

    @Transactional
    public void modificarPaciente(MultipartFile archivo, String idPaciente, String email, String contraseña, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            Sexo sexo, String telefono, ObraSocial obraSocial, String idHistoriaClinica, String idProfesional, String idTurno) throws Excepcion {

        validar(email, contraseña, nombre, apellido, dni, fechaDeNacimiento, sexo, telefono, obraSocial, idProfesional, idTurno);
        
        Optional<HistoriaClinica> respuestaHistoriaClinica = historiaClinicaRepositorio.findById(idHistoriaClinica);
        Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);
        Optional<Turno> respuestaTurno = turnoRepositorio.findById(idTurno);
        Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(idPaciente);

        HistoriaClinica historiaClinica = new HistoriaClinica();
        Profesional profesional = new Profesional();
        Turno turno = new Turno();

        if (respuestaHistoriaClinica.isPresent()) {

            historiaClinica = respuestaHistoriaClinica.get();
        }

        if (respuestaProfesional.isPresent()) {

            profesional = respuestaProfesional.get();
        }

        if (respuestaTurno.isPresent()) {

            turno = respuestaTurno.get();
        }

        if (respuestaPaciente.isPresent()) {

            Paciente paciente = respuestaPaciente.get();

            paciente.setEmail(email);
            paciente.setContraseña(contraseña);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setFechaDeNacimiento(fechaDeNacimiento);
            paciente.setTelefono(telefono);
            paciente.setEnable(true);
            paciente.setFechaCreacion(new Date());
            paciente.setRol(Rol.PACIENTE);
            paciente.setSexo(sexo);
            paciente.setObraSocial(obraSocial);
            paciente.setHistoriaClinica(historiaClinica);
            paciente.setProfesional(profesional);
            paciente.setTurno(turno);

            String idImagen = null;
            
            if(paciente.getImagen() != null){
            
                idImagen = paciente.getImagen().getId();
            }
            
            Imagen imagen = imagenServio.actualizar(archivo, idImagen);

            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);

        }

    }
    
    public void bajaPaciente(boolean enable, String idPaciente){
    
        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);
        
        if(respuesta.isPresent()){
        
            Paciente paciente = respuesta.get();
            
            paciente.setEnable(enable);
        }
        
    }

    public void validar(String email, String contraseña, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            Sexo sexo, String telefono, ObraSocial obraSocial, String idProfesional, String idTurno) throws Excepcion{

        if(email == null || email.isEmpty()){
        
            throw new Excepcion("Ingrese un email");
        }
        
        if(contraseña == null || contraseña.isEmpty()){
        
            throw new Excepcion("Ingrese una contraseña");
        }
        
        if(nombre == null || nombre.isEmpty()){
        
            throw new Excepcion("Ingrese su nombre");
        }
        
        if(apellido == null || apellido.isEmpty()){
        
            throw new Excepcion("Ingrese su apellido");
        }
        
        if(dni == null || dni.isEmpty()){
        
            throw new Excepcion("Ingrese su dni");
        }
        
        if(fechaDeNacimiento == null){
        
            throw new Excepcion("Ingrese su fecha de nacimiento");
        }
        
        if(telefono == null || telefono.isEmpty()){
        
            throw new Excepcion("Ingrese un contacto");
        }
        
        if(sexo == null){
        
            throw new Excepcion("Ingrese su sexo");
        }
        
        if(idProfesional == null || idProfesional.isEmpty()){
        
            throw new Excepcion("Debe asigarse un prosefional");
        }
        
        if(idTurno == null || idTurno.isEmpty()){
        
            throw new Excepcion("Debe elegir un turno");
        }
        
        if(obraSocial == null){
        
            throw new Excepcion("Debe asignar una obra social");
        }
        
        
        
    }
}

