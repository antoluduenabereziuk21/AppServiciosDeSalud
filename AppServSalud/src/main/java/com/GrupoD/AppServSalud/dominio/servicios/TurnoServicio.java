/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.dominio.repositorio.OfertaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.TurnoRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leand
 */
@Service
public class TurnoServicio {
    
    @Autowired
    private TurnoRepositorio turnoRepositorio;
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @Autowired
    private OfertaRepositorio ofertaRepositorio;
    
    public void crearTurno(Date fechaTurno, String idPaciente, String idProfesional, String idOferta) throws MiExcepcion{

        if(fechaTurno == null){
            throw new MiExcepcion("Debe seleccionar un turno");
        }
        
        Profesional profesional = profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new MiExcepcion("No se encontro ningun profesional"));
        Paciente paciente = pacienteRepositorio.findById(idPaciente).orElseThrow(()-> new MiExcepcion("No se encontro ningun paciente"));
        Oferta oferta = ofertaRepositorio.findById(idOferta).orElseThrow(()-> new MiExcepcion("No se encontro ninguna oferta"));
        
        Turno turno = new Turno();
        
        turno.setFechaTurno(fechaTurno);
        turno.setFechaAlta(new Date());
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setEstado(false);
        turno.setOferta(oferta);
        
        if(paciente.getActivo()== true){
            turno.setActivoPaciente(true);
        }else{
           throw new MiExcepcion("El paciente no esta activo");
        }
        
        if(profesional.getActivo()==true){
            turno.setActivoProfesional(true);
        }else{
           throw new MiExcepcion("El profesional no esta activo");
        }
        
        turnoRepositorio.save(turno);
    }

    public List<Turno> listarTurnos(String idProfesional) throws MiExcepcion{
    
            return profesionalRepositorio.findById(idProfesional).orElseThrow(()->new MiExcepcion("No existe profesional con esa id")).getTurnos();
    }

}
