package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.Turno;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroTurno;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
    @Query("SELECT t FROM Turno t WHERE t.paciente.email = :email")
    List<Turno> listarTurnosPorPaciente(@Param("email") String email);

    @Query(
        "SELECT t FROM Turno t WHERE " +
        "t.paciente.email = :#{#filtro.paciente.email} AND " +
        "t.estado in :#{#filtro.estados}"
    )
    List<Turno> listarPorEstadoDelTurno(@Param("filtro") FiltroTurno filtro);

    @Query("SELECT t FROM Turno t WHERE t.profesional.id = :id")
    List<Turno> listarTurnosPorProfesional(@Param("id") String id);


    @Query(
        "SELECT t.id FROM Turno t WHERE "+
        "t.paciente.id = :#{#filtro.paciente.id} AND "+
        "t.oferta.fecha = :#{#filtro.oferta.fecha} AND "+
        "t.oferta.horario = :#{#filtro.oferta.horario} AND "+
        "t.estado in :#{#filtro.estados}"
    )
    List<String> filtrarTrunosPorDiaYFecha(@Param("filtro") FiltroTurno filtro);

    @Query(
        "SELECT t.id FROM Turno t WHERE "+
        "t.paciente.id = :#{#filtro.paciente.id} AND "+
        "t.profesional.id = :#{#filtro.profesional.id} AND "+
        "t.oferta.fecha = :#{#filtro.oferta.fecha} AND " +
        "t.estado in :#{#filtro.estados}"
    )
    List<String> filtrarTurnosPorDiayProfesional(@Param("filtro") FiltroTurno filtro);
}
