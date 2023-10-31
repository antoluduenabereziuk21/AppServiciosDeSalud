package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String>{

    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    Optional<Paciente> buscarPorDni(@Param("dni")String dni);

    @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.apellido = :apellido AND p.activo = true")
    List<Paciente> buscarPorNombreYApellidoActivos(@Param("nombre")String nombre, @Param("apellido")String apellido);

    @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.apellido = :apellido AND p.activo = false")
    List<Paciente> buscarPorNombreYApellidoInactivos(@Param("nombre")String nombre, @Param("apellido")String apellido);

    @Query("SELECT p FROM Paciente p WHERE p.activo = true")
    List<Paciente> buscarActivos();

    @Query("SELECT p FROM Paciente p WHERE p.activo = false")
    List<Paciente> buscarInactivos();


}
