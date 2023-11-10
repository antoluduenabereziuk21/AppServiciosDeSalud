/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.OfertaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leand
 */
@Service
public class OfertaServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private OfertaRepositorio ofertaRepositorio;
    
    
    public void crearOferta(TipoConsultaEnum tipo,String detalle,HorarioEnum horario,String ubicacion,
            Double precio, String idProfesional) throws MiExcepcion{
    
        validacion(tipo, detalle, horario, ubicacion, precio);
        
        Profesional profesional = profesionalRepositorio.findById(idProfesional).orElseThrow(()-> new MiExcepcion("No se encontro ningun profesional"));
        
        Oferta oferta = new Oferta();
        
        oferta.setProfesional(profesional);
        oferta.setHorario(horario);
        oferta.setTipo(tipo);
        oferta.setUbicacion(ubicacion);
        oferta.setDetalle(detalle);
        oferta.setPrecio(precio);
        
        ofertaRepositorio.save(oferta);
    }
    
    public List<Oferta> listarOferta(){
    
       return ofertaRepositorio.findAll();
    }
    
    public List<Oferta> listarPorTipo(TipoConsultaEnum tipo){
    
        return ofertaRepositorio.buscarPorTipo(tipo);
    }

    public void validacion(TipoConsultaEnum tipo,String detalle,HorarioEnum horario,String ubicacion,
            Double precio) throws MiExcepcion{
    
        if(tipo == null){
            throw new MiExcepcion("Elija un tipo de consulta");
        }
        
        if(horario == null){
            throw new MiExcepcion("Elija una franja horaria");
        }
        
        if(detalle == null || detalle.isEmpty()){
            throw new MiExcepcion("Dejar una discripcion de su profesion");
        }
        
        if(ubicacion == null || ubicacion.isEmpty()){
            throw new MiExcepcion("Ingrese una ubicacion de trabajo");
        }
        
        if(precio == null){
            throw new MiExcepcion("Ingrese el precio de la consulta");
        }
        
        
        
    }

    
}
