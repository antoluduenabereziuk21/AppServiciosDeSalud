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

    @Query("SELECT p FROM Paciente p WHERE p.email = :email")
    Optional<Paciente> buscarPorEmail(@Param("email")String email);
    
    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    Optional<Paciente> buscarPorDni(@Param("dni")String dni);

    @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.activo = :activo")
    List<Paciente> buscarPorNombre(@Param("nombre")String nombre, @Param("activo") boolean activo);

    @Query("SELECT p FROM Paciente p WHERE p.apellido = :apellido AND p.activo = :activo")
    List<Paciente> buscarPorApellido(@Param("apellido")String apellido, @Param("activo") boolean activo);

    @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.apellido = :apellido AND p.activo = :activo")
    List<Paciente> buscarPorNombreYApellido(@Param("nombre")String nombre, @Param("apellido")String apellido,@Param("activo") boolean activo);

    @Query("SELECT p FROM Paciente p WHERE p.activo = :activo")
    List<Paciente> listar(@Param("activo") boolean activo);


}
