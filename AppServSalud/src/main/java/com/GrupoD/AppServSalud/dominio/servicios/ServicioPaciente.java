package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Imagen;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;

import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.UsuarioRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.ObraSocialEnum;
import com.GrupoD.AppServSalud.utilidades.RolEnum;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.GrupoD.AppServSalud.utilidades.Sexo;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioPaciente {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    /*
     * 
     * @Autowired
     * private HistoriaClinicaRepositorio historiaClinicaRepositorio;
     * 
     * @Autowired
     * private ProfesionalRepositorio profesionalRepositorio;
     * 
     * @Autowired
     * private TurnoRepositorio turnoRepositorio;
     */

    public Paciente buscarPorEmail(String email) {
        return pacienteRepositorio.buscarPorEmail(email).get();
    }

    @Transactional
    public void crearPaciente(String email, String contrasenha, String nombre, String apellido,
            /*
             * String dni, Date fechaDeNacimiento, String sexo, String telefono
             * ,MultipartFile archivo, String idHistoriaClinica,
             * String idProfesional, String idTurno) throws Excepcion {
             */

            String dni, Date fechaDeNacimiento, String sexo, String telefono
    /*
     * ,MultipartFile archivo, String idHistoriaClinica,
     * String idProfesional, String idTurno
     */) throws MiExcepcion {

        if (usuarioRepositorio.buscarPorEmail(email).isPresent()) {
            throw new MiExcepcion("El email ya se encuentra registrado");
        }

        if (usuarioRepositorio.buscarPorDni(dni).isPresent()) {
            throw new MiExcepcion("El DNI ya se encuentra registrado");
        }

        Validacion.validarStrings(email, contrasenha, nombre, apellido, dni, sexo, telefono);
        Validacion.validarDate(fechaDeNacimiento);

        /*
         * HistoriaClinica historiaClinica =
         * historiaClinicaRepositorio.findById(idHistoriaClinica).get();
         * Profesional profesional =
         * profesionalRepositorio.findById(idProfesional).get();
         * Turno turno = turnoRepositorio.findById(idTurno).get();
         */
        Paciente paciente = new Paciente();

        setearParametros(email, contrasenha, nombre, apellido, dni, fechaDeNacimiento, sexo, telefono, "PARTICULAR",
                paciente);
        /*
         * paciente.setHistoriaClinica(historiaClinica);
         * paciente.setProfesional(profesional);
         * paciente.setTurno(turno);
         * 
         * Imagen imagen = imagenServio.Guardar(archivo);
         * 
         * paciente.setImagen(imagen);
         */
        pacienteRepositorio.save(paciente);
    }

    @Transactional
    public void modificarPaciente(MultipartFile archivo, String idPaciente, String nombre, String apellido,
            String sexo, String telefono, String obraSocial) throws MiExcepcion {
        // , String idHistoriaClinica, String idProfesional, String idTurno
        Validacion.validarStrings(nombre, apellido, sexo, telefono, obraSocial);

        Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(idPaciente);
        /*
         * Optional<HistoriaClinica> respuestaHistoriaClinica =
         * historiaClinicaRepositorio.findById(idHistoriaClinica);
         * Optional<Profesional> respuestaProfesional =
         * profesionalRepositorio.findById(idProfesional);
         * Optional<Turno> respuestaTurno = turnoRepositorio.findById(idTurno);
         * HistoriaClinica historiaClinica = new HistoriaClinica();
         * Profesional profesional = new Profesional();
         * Turno turno = new Turno();
         * 
         * if (respuestaHistoriaClinica.isPresent()) {
         * 
         * historiaClinica = respuestaHistoriaClinica.get();
         * }
         * 
         * if (respuestaProfesional.isPresent()) {
         * 
         * profesional = respuestaProfesional.get();
         * }
         * 
         * if (respuestaTurno.isPresent()) {
         * 
         * turno = respuestaTurno.get();
         * }
         */
        if (respuestaPaciente.isPresent()) {

            Paciente paciente = respuestaPaciente.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            if (!sexo.isEmpty() || sexo != null) {
                paciente.setSexo(Sexo.valueOf(sexo));
            }
            paciente.setTelefono(telefono);
            if (!obraSocial.isEmpty() || obraSocial != null) {
                paciente.setObraSocial(ObraSocialEnum.valueOf(obraSocial));
            }

            if (!archivo.isEmpty()) {
                String idImagen = null;

                if (paciente.getImagen() != null) {

                    idImagen = paciente.getImagen().getId();
                }

                Imagen imagen = null;

                try {
                    imagen = imagenServicio.actualizar(archivo, idImagen);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                paciente.setImagen(imagen);
            }
            /*
             * paciente.setHistoriaClinica(historiaClinica);
             * paciente.setProfesional(profesional);
             * paciente.setTurno(turno);
             * 
             */
            pacienteRepositorio.save(paciente);

        }

    }

    private void setearParametros(String email, String contrasenha, String nombre, String apellido, String dni,
            Date fechaDeNacimiento, String sexo, String telefono, String obraSocial, Paciente paciente) {
        paciente.setEmail(email);
        paciente.setPassword(new BCryptPasswordEncoder().encode(contrasenha));
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaDeNacimiento);
        paciente.setTelefono(telefono);
        paciente.setActivo(true);
        paciente.setRol(RolEnum.PACIENTE);
        paciente.setSexo(Sexo.valueOf(sexo));
        paciente.setObraSocial(ObraSocialEnum.valueOf(obraSocial));
    }

    public Paciente buscarPorDni(String dni) {

        Optional<Paciente> respuestaPaciente = pacienteRepositorio.buscarPorDni(dni);
        if (respuestaPaciente.isPresent()) {
            return respuestaPaciente.get();
        }
        return null;
    }

    public void bajaPaciente(boolean enable, String idPaciente) {

        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setActivo(enable);

            pacienteRepositorio.save(paciente);
        }

    }

    public List<Paciente> listarPacientes(boolean activo) {
        return pacienteRepositorio.listar(activo);
    }

    public List<Paciente> filtrarPacientes(FiltroUsuario usuario) {
        return pacienteRepositorio.buscarPorFiltro(usuario);
    }

    public void cambiarContrasenha(String email, String newPassword) {

        Optional<Paciente> respuesta = pacienteRepositorio.buscarPorEmail(email);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setPassword(new BCryptPasswordEncoder().encode(newPassword));

            pacienteRepositorio.save(paciente);
        }
    }

    public Paciente findById(String id) {
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }

    public void modificarPacienteAdmin(String emailPath, String email, String nombre, String apellido, String dni, String sexo,
            String telefono, Date dateFecha, String obraSocial) throws MiExcepcion{
        Validacion.validarStrings(nombre, apellido, dni, sexo, telefono, obraSocial);
        
        Optional<Paciente> respuestaPaciente = pacienteRepositorio.buscarPorEmail(emailPath);

        if (respuestaPaciente.isPresent()) {

            Paciente paciente = respuestaPaciente.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setSexo(Sexo.valueOf(sexo));
            paciente.setTelefono(telefono);
            paciente.setFechaNacimiento(dateFecha);
            paciente.setEmail(email);
            paciente.setObraSocial(ObraSocialEnum.valueOf(obraSocial));

            pacienteRepositorio.save(paciente);
        }else{
            throw new MiExcepcion("No se encontro el paciente");
        }

    }
}
