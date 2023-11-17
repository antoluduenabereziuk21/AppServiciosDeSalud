package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.List;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.RegistroConsulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT p FROM Paciente p JOIN p.historiaClinica hc WHERE hc.profesional.id = :profesionalId")
    List<Paciente> findPacientesConHistoriaClinicaByProfesional(@Param("profesionalId") String profesionalId);

    @Query("SELECT hc FROM HistoriaClinica hc WHERE hc.profesional.id = :profesionalId")
    List<HistoriaClinica> findHistoriasClinicasByProfesional(@Param("profesionalId") String profesionalId);

    @Query("SELECT rc FROM RegistroConsulta rc WHERE rc.historiaClinica.paciente.id = :pacienteId AND rc.profesional.id = :profesionalId")
    List<RegistroConsulta> findByPacienteAndProfesional(@Param("pacienteId") String pacienteId,
            @Param("profesionalId") String profesionalId);

}
