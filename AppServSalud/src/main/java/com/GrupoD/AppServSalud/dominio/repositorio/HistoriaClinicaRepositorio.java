package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository <HistoriaClinica, String> {
    /*
    @Query("SELECT h FROM HistoriaClinica h WHERE h.Profesional.email = :email")
    List<HistoriaClinica> buscarPorProfesional (@Param("email")String email);

    @Query("SELECT h FROM HistoriaClinica h WHERE h.Paciente.email = :email")
    Optional<HistoriaClinica> buscarPorPaciente (@Param("email")String email);
    
    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.dni =: dni")
    Optional <HistoriaClinica> buscarPacientePorDni (@Param ("dni") String dni);*/


    /*
     Espero que sirvan lucas

     */
    @Query("SELECT hc FROM HistoriaClinica hc JOIN hc.paciente p JOIN p.profesional pr WHERE pr.id = :profesionalId")
    List<HistoriaClinica> findHistoriasClinicasByProfesional(@Param("profesionalId") String profesionalId);


     @Query("SELECT p FROM Paciente p JOIN p.historiaClinica hc JOIN hc.profesional pr WHERE pr.id = :profesionalId")
    List<Paciente> findPacientesAtendidosPorProfesional(@Param("profesionalId") String profesionalId);


}
