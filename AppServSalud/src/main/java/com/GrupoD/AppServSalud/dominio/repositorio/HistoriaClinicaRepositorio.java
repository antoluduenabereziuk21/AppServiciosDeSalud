package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository <HistoriaClinica, String> {
    
    @Query("SELECT h FROM HistoriaClinica h WHERE h.Profesional.email = :email")
    List<HistoriaClinica> buscarPorProfesional (@Param("email")String email);

    @Query("SELECT h FROM HistoriaClinica h WHERE h.Paciente.email = :email")
    Optional<HistoriaClinica> buscarPorPaciente (@Param("email")String email);

}
