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

    @Autowired
    private NotificacionServicio notificacionServicio;

    public List<Turno> listarTurnosPorPaciente(String email) throws MiExcepcion{
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email del paciente no puede ser nulo");
        }
        return turnoRepositorio.listarTurnosPorPaciente(email);
    }

    public List<Turno> listarTurnosPorProfesional(String idProfesional){
        return turnoRepositorio.listarTurnosPorProfesional(idProfesional);
    }

    public void aceptarTurno(String idTurno) throws MiExcepcion {

        if (idTurno == null) {
            throw new MiExcepcion("El id del turno no puede ser nulo");
        }

        Turno turno = turnoRepositorio.findById(idTurno).orElseThrow(
                () -> new MiExcepcion("No se encontro el turno con id: " + idTurno)
        );
        
        turno.setActivoProfesional(true);
        turnoRepositorio.save(turno);

        notificacionServicio.crearNotificacionEstadoTurno(turno.getPaciente().getId(), turno.getProfesional().getId(), true);
    }

}
