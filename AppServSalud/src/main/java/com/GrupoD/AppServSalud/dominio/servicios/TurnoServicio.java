package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.dominio.repositorio.OfertaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.TurnoRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.EstadoTurno;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroTurno;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    public List<Turno> listarTurnosPorPaciente(String email) throws MiExcepcion {
        Validacion.validarStrings(email);
        return turnoRepositorio.listarTurnosPorPaciente(email);
    }

    public List<Turno> listarPorEstadoDelTurno(String email, EstadoTurno[] estados) throws MiExcepcion {
        Validacion.validarStrings(email);
        if(estados == null || estados.length == 0){
            throw new MiExcepcion("El estado del turno no puede ser nulo");
        }
        FiltroTurno filtro = new FiltroTurno();
        Paciente paciente = new Paciente();
        paciente.setId(email);
        filtro.setPaciente(paciente);
        filtro.setEstados(estados);
        return turnoRepositorio.listarPorEstadoDelTurno(filtro);
    }

    public List<Turno> listarTurnosPorProfesional(String idProfesional) {
        return turnoRepositorio.listarTurnosPorProfesional(idProfesional);
    }

    @Transactional
    public void cancelarTurno(String idTurno) throws MiExcepcion {
        if (idTurno == null) {
            throw new MiExcepcion("El id del turno no puede ser nulo");
        }

        Turno turno = turnoRepositorio.findById(idTurno).orElseThrow(
                () -> new MiExcepcion("No se encontró el turno con id: " + idTurno));

        Oferta oferta = turno.getOferta();

        if (oferta != null) {
            oferta.setReservado(false);

            ofertaRepositorio.save(oferta);
            turno.setEstado(EstadoTurno.CANCELADO_PACIENTE);
            notificacionServicio.crearNotificacionCancelacionTurno(turno.getPaciente().getId(),
                    turno.getProfesional().getId());
            
        }
    }

    
    public void aceptarTurno(String idTurno) throws MiExcepcion {

        if (idTurno == null) {
            throw new MiExcepcion("El id del turno no puede ser nulo");
        }

        Turno turno = turnoRepositorio.findById(idTurno).orElseThrow(
                () -> new MiExcepcion("No se encontro el turno con id: " + idTurno));

        turno.setEstado(EstadoTurno.CONFIRMADO);
        turnoRepositorio.save(turno);

        notificacionServicio.crearNotificacionEstadoTurno(turno.getPaciente().getId(), turno.getProfesional().getId(),
                true);
    }

    public void crearTurno(Oferta oferta, Paciente paciente) throws MiExcepcion {

        FiltroTurno filtro = new FiltroTurno();
        filtro.setPaciente(paciente);
        filtro.setProfesional(oferta.getProfesional());
        filtro.setOferta(oferta);
        filtro.setEstados(new EstadoTurno[] { EstadoTurno.EN_ESPERA, EstadoTurno.CONFIRMADO });

        List<String> resultadoFiltro1 = turnoRepositorio.filtrarTrunosPorDiaYFecha(filtro);

        if (!resultadoFiltro1.isEmpty()) {
            throw new MiExcepcion("Ya existe un turno para el dia y horario seleccionado");
        }

        List<String> resultadoFiltro2 = turnoRepositorio.filtrarTurnosPorDiayProfesional(filtro);

        if (!resultadoFiltro2.isEmpty()) {
            throw new MiExcepcion("Ya existe un turno para el dia y profesional seleccionado");
        }

        Turno turno = new Turno();

        turno.setFechaAlta(new Date());
        turno.setFechaTurno(oferta.getFecha());
        turno.setHoraTurno(oferta.getHorario());
        turno.setEstado(EstadoTurno.EN_ESPERA);
        turno.setProfesional(oferta.getProfesional());
        turno.setPaciente(paciente);
        turno.setOferta(oferta);

        turnoRepositorio.save(turno);

    }

    public List<Turno> filtrarPorEstadoYPaciente(String email, String estado) throws MiExcepcion{
        Validacion.validarStrings(email,estado);
        FiltroTurno filtro = new FiltroTurno();
        Paciente paciente = new Paciente();
        paciente.setEmail(email);
        filtro.setPaciente(paciente);
        filtro.setEstados(new EstadoTurno[]{EstadoTurno.valueOf(estado)});
        return turnoRepositorio.listarPorEstadoDelTurno(filtro);
    }

}
