package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;

import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.ObraSocialEnum;
import com.GrupoD.AppServSalud.utilidades.RolEnum;
import java.util.Date;
import java.util.Optional;

import com.GrupoD.AppServSalud.utilidades.Sexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ServicioPaciente {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    /*
    @Autowired
    private ImagenServicio imagenServio;
    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private TurnoRepositorio turnoRepositorio;
    */

    @Transactional
    public void crearPaciente(String email, String contraseña, String nombre, String apellido,
                              String dni, Date fechaDeNacimiento, String sexo, String telefono,
                              String obraSocial /*,MultipartFile archivo, String idHistoriaClinica,
                              String idProfesional, String idTurno*/) throws MiExcepcion {

        obraSocial = "SwissMedical";
        validar(email, contraseña, nombre, apellido, dni, fechaDeNacimiento, sexo, telefono, obraSocial);

        /*
        HistoriaClinica historiaClinica = historiaClinicaRepositorio.findById(idHistoriaClinica).get();
        Profesional profesional = profesionalRepositorio.findById(idProfesional).get();
        Turno turno = turnoRepositorio.findById(idTurno).get();
        */
        Paciente paciente = new Paciente();

        paciente.setEmail(email);
        paciente.setPassword(contraseña);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaDeNacimiento);
        paciente.setTelefono(telefono);
        paciente.setActivo(true);
        paciente.setFechaAlta(new Date());
        paciente.setRol(RolEnum.PACIENTE);
        paciente.setSexo(Sexo.valueOf(sexo));
        paciente.setObraSocial(ObraSocialEnum.SwissMedical);
        /*
        paciente.setHistoriaClinica(historiaClinica);
        paciente.setProfesional(profesional);
        paciente.setTurno(turno);

        Imagen imagen = imagenServio.Guardar(archivo);

        paciente.setImagen(imagen);
        */
        pacienteRepositorio.save(paciente);
    }

    @Transactional
    public void modificarPaciente(MultipartFile archivo, String idPaciente, String email, String contraseña, String nombre, String apellido, String dni, Date fechaDeNacimiento,
            String sexo, String telefono, String obraSocial, String idHistoriaClinica, String idProfesional, String idTurno) throws MiExcepcion {

        validar(email, contraseña, nombre, apellido, dni, fechaDeNacimiento, sexo, telefono, obraSocial);
        Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(idPaciente);
        /*
        Optional<HistoriaClinica> respuestaHistoriaClinica = historiaClinicaRepositorio.findById(idHistoriaClinica);
        Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);
        Optional<Turno> respuestaTurno = turnoRepositorio.findById(idTurno);
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
        */
        if (respuestaPaciente.isPresent()) {

            Paciente paciente = respuestaPaciente.get();

            paciente.setEmail(email);
            paciente.setPassword(contraseña);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setFechaNacimiento(fechaDeNacimiento);
            paciente.setTelefono(telefono);
            paciente.setActivo(true);
            paciente.setRol(RolEnum.PACIENTE);
            paciente.setSexo(Sexo.valueOf(sexo));
            paciente.setObraSocial(ObraSocialEnum.valueOf(obraSocial));
            /*
            paciente.setHistoriaClinica(historiaClinica);
            paciente.setProfesional(profesional);
            paciente.setTurno(turno);

            String idImagen = null;
            
            if(paciente.getImagen() != null){
            
                idImagen = paciente.getImagen().getId();
            }
            
            Imagen imagen = imagenServio.actualizar(archivo, idImagen);

            paciente.setImagen(imagen);
            */
            pacienteRepositorio.save(paciente);

        }

    }
    
    public void bajaPaciente(boolean enable, String idPaciente){
    
        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);
        
        if(respuesta.isPresent()){
        
            Paciente paciente = respuesta.get();
            
            paciente.setActivo(enable);
        }
        
    }

    public void validar(String email, String contraseña, String nombre, String apellido, String dni,
                        Date fechaDeNacimiento, String sexo, String telefono, String obraSocial) throws MiExcepcion{

        if(email == null || email.isEmpty()){
        
            throw new MiExcepcion("Ingrese un email");
        }
        
        if(contraseña == null || contraseña.isEmpty()){
        
            throw new MiExcepcion("Ingrese una contraseña");
        }
        
        if(nombre == null || nombre.isEmpty()){
        
            throw new MiExcepcion("Ingrese su nombre");
        }
        
        if(apellido == null || apellido.isEmpty()){
        
            throw new MiExcepcion("Ingrese su apellido");
        }
        
        if(dni == null || dni.isEmpty()){
        
            throw new MiExcepcion("Ingrese su dni");
        }
        
        if(fechaDeNacimiento == null){
        
            throw new MiExcepcion("Ingrese su fecha de nacimiento");
        }
        
        if(telefono == null || telefono.isEmpty()){
        
            throw new MiExcepcion("Ingrese un contacto");
        }
        
        if(sexo == null){
        
            throw new MiExcepcion("Ingrese su sexo");
        }
        
        if(obraSocial == null){
        
            throw new MiExcepcion("Debe asignar una obra social");
        }
        
        
        
    }


}

