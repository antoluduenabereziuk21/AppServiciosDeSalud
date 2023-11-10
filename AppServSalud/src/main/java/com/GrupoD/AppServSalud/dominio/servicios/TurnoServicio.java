package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.dominio.repositorio.TurnoRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {
    
    @Autowired
    private TurnoRepositorio turnoRepositorio;

    public List<Turno> listarTurnosPorPaciente(String idPaciente) throws MiExcepcion{
        return turnoRepositorio.listarTurnosPorPaciente(idPaciente);
    }

}
