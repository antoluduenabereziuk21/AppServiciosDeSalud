package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.dominio.repositorio.TurnoRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroTurno;

import java.util.Date;
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

    public void crearTurno(Oferta oferta, Paciente paciente) throws MiExcepcion {

        FiltroTurno filtro = new FiltroTurno(paciente,oferta.getProfesional(),oferta);

        List<String> resultadoFiltro1 = turnoRepositorio.filtrarTrunosPorDiaYFecha(filtro);

        if (!resultadoFiltro1.isEmpty()){
            throw new MiExcepcion("Ya existe un turno para el dia y horario seleccionado");
        }

        List<String> resultadoFiltro2 = turnoRepositorio.filtrarTurnosPorDiayProfesional(filtro);

        if (!resultadoFiltro2.isEmpty()){
            throw new MiExcepcion("Ya existe un turno para el dia y profesional seleccionado");
        }

        Turno turno = new Turno();

        turno.setFechaAlta(new Date());
        turno.setFechaTurno(oferta.getFecha());
        turno.setHoraTurno(oferta.getHorario());
        turno.setEstado(true);
        turno.setActivoPaciente(true);
        turno.setActivoProfesional(false);
        turno.setProfesional(oferta.getProfesional());
        turno.setPaciente(paciente);
        turno.setOferta(oferta);

        turnoRepositorio.save(turno);

    }

}
