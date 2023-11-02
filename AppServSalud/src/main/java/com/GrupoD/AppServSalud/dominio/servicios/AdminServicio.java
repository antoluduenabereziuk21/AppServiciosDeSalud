/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.GrupoD.AppServSalud.utilidades.Validacion;

/**
 *
 * @author antolube20
 */
@Service
public class AdminServicio {
    
    @Autowired
    private AdminRepositorio adminRepositorio;
    @Autowired 
    private ProfesionalRespositorio profesionalRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    
    @Transactional
    public void crearAdmin(){}
    
    @Transactional
    public void crearProfesional(String email,
            String contrasenha, String nombre, String apellido, String dni, 
            Date fechaDeNacimiento, String sexo, String telefono,
            String especialidad  ) throws MiExcepcion{
        
    Validacion.validarStrings(email, contrasenha, nombre, apellido, dni, sexo, telefono);
    Validacion.validarDate(fechaDeNacimiento);
    
    Profesional profesional = new profesional();
    
   
    

    }
    
    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales(){
        return null;
    }
    
    @Transactional(readOnly = true)
    public List<Paciente> listarPacientes(){
        return pacienteRepositorio.listar(true);
    }
    
    
    
    
    
    
}
