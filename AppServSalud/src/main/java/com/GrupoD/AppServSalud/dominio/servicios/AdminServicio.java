/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.GrupoD.AppServSalud.utilidades.Validacion;

/**
 *
 * @author antolube20
 */
@Service
public class AdminServicio {
    /*
    Descomentar Luego de Creada Entidad Y Respositorio
    @Autowired
    private AdminRepositorio adminRepositorio;

     */
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private ServicioPaciente servicioPaciente;

    
    
    @Transactional
    public void crearAdmin(String email, String contrasenha, String nombre, String apellido, String role){
        usuarioServicio.createAdminUser(email,contrasenha,nombre,apellido,role);
    }
    /*
    //Descomentar luego de creada Entidad y Repositorio
    @Transactional

    public void modficarDatosAdmin(String idAdmin,String email, String contrasenha, String nombre, String apellido, String role){
        Optional<Admin> respuestaAdmin= adminRepositorio.findById(idAdmin);
        if (respuestaAdmin.isPresent()){
            Admin admin = respuestaAdmin.get();
            admin.setEmail(email);
            admin.setContresenha(email);
            admin.setNombre(nombre);
            admin.setRol(RolEnum.valueOf(role));

             adminRepositorio.save(admin);
        }

    }

    @Transactional
    public void modificarEstadoAdmin(boolean enable,String idAdmin){
        Optional<Admin> respuesta = adminRepositorio.findById(idAdmin);

        if(respuesta.isPresent()){

            Admin admin = respuesta.get();

            admin.setActivo(enable);

            adminRepositorio.save(admin);
        }
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradores(){
        return adminRepositorio.findAll();
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarActivos();
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarInctivos();
    }
    */
    @Transactional
    public void crearProfesional(String nombre, String apellido, String dni,
                                 Date fechaDeNacimiento, String email,
                                 String sexo, String telefono, String password ) throws MiExcepcion{
        profesionalServicio.crearProfesional(nombre,apellido,dni,fechaDeNacimiento,email,sexo,telefono,password);
    }
    @Transactional
    public void modificarEstadoProfesional(boolean enable,String idProfesional){
        profesionalServicio.bajaProfesional(enable,idProfesional);
    }
    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales(){
        return profesionalRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionalActivo(){
        return profesionalRepositorio.buscarActivos();
    }
    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionalInactivo(){
        return profesionalRepositorio.buscarInactivos();
    }

    @Transactional
    public void modificarEstadoPaciente(boolean enable, String idPaciente){
        servicioPaciente.bajaPaciente(enable,idPaciente);
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientesActivos(){
        return pacienteRepositorio.listar(true);
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarPacientesInactivos(){
        return pacienteRepositorio.listar(false);
    }

}
