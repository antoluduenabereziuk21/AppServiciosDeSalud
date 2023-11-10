package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GrupoD.AppServSalud.dominio.entidades.Turno;

public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
    @Query("SELECT t FROM Turno t WHERE t.paciente.email = :email")
    List<Turno> listarTurnosPorPaciente(@Param("email") String email);

    @Query("SELECT t FROM Turno t WHERE t.profesional.id = :id")
    List<Turno> listarTurnosPorProfesional(@Param("id") String id);
}
