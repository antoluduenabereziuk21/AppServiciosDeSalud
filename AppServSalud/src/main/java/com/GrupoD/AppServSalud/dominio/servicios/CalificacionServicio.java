package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Calificacion;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServicio {
    
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    public void guardarCalificacion(int calificacion,String idProfesional,String idPaciente,String comentario) throws MiExcepcion{

        validarCalificacion(calificacion);
        
        Profesional profesional = profesionalRepositorio.findById(idProfesional).orElseThrow(()->new MiExcepcion("No existe profesional con esa id"));
        Paciente paciente = pacienteRepositorio.findById(idPaciente).orElseThrow(()->new MiExcepcion("No existe paciente con esa id"));
        
        Calificacion c = new Calificacion();
        
        c.setPaciente(paciente);
        c.setComentario(comentario);
        c.setValor(calificacion);
        profesional.getCalificaciones().add(c);

        profesionalRepositorio.save(profesional);
}
    
    public List<Calificacion> listaCalificacion(String idProfesional) throws MiExcepcion{
    
        return profesionalRepositorio.findById(idProfesional).orElseThrow(()->new MiExcepcion("No existe profesional con esa id")).getCalificaciones();
    }
   
    public void validarCalificacion(Integer calificacion) throws MiExcepcion{
    
        if(calificacion == null){
            throw new MiExcepcion("Falta la calificacion del profesional");
        }
    }
}
