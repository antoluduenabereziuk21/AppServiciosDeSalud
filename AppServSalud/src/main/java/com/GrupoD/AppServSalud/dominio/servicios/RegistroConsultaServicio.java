/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.RegistroConsulta;
import com.GrupoD.AppServSalud.dominio.repositorio.HistoriaClinicaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.RegistroConsultaRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author leand
 */
@Service
public class RegistroConsultaServicio {
    
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private RegistroConsultaRepositorio registroConsultaRepositorio;
    
    @Transactional
    public void crearRegistroDeConsulta(String idProfesional, String detalleConsulta, String dni) throws MiExcepcion{
    
        Profesional profesional = profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional"));
            
        Optional<HistoriaClinica> historiaClinicaRepuesta = historiaClinicaRepositorio.buscarPacientePorDni(dni);
        

        if (historiaClinicaRepuesta.isPresent()) {
            
            HistoriaClinica historiaClinica = historiaClinicaRepuesta.get();
             
            RegistroConsulta registroConsulta = new RegistroConsulta();
            registroConsulta.setProfesional(profesional);
            registroConsulta.setDetalleConsulta(detalleConsulta);
            historiaClinica.getRegistroConsulta().add(registroConsulta);
            
            historiaClinicaRepositorio.save(historiaClinica);
        } else {
             throw new MiExcepcion("No se encontro ninguna historia clinica con el numero de dni " + dni);
        }
    }
    
    @Transactional
    public void modificar(String idProfesional,String detalleConsulta,
                String idRegistroConsulta, String dni) throws MiExcepcion{
    
        RegistroConsulta registroConsulta = registroConsultaRepositorio.findById(idRegistroConsulta)
                .orElseThrow(()-> new MiExcepcion("No se encontro registros de consulta"));
        
        Profesional profesional = profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional"));

        Optional<HistoriaClinica> historiaClinicaRepuesta = historiaClinicaRepositorio.buscarPacientePorDni(dni);
        

        if (historiaClinicaRepuesta.isPresent()) {
            
            HistoriaClinica historiaClinica = historiaClinicaRepuesta.get();
             
            registroConsulta.setProfesional(profesional);
            registroConsulta.setDetalleConsulta(detalleConsulta);
            historiaClinica.getRegistroConsulta().add(registroConsulta);
            
            historiaClinicaRepositorio.save(historiaClinica);
        } else {
             throw new MiExcepcion("No se encontro ninguna historia clinica con el numero de dni " + dni);
        }
    }
    
    public List<RegistroConsulta> listarRegistroDeConsultas(){
    
        return registroConsultaRepositorio.findAll();
    }
    
  
}
