package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String>{

    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    Optional<Paciente> buscarPorDni(@Param("dni")String dni);

}
