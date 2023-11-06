package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
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

    @Query("SELECT p FROM Paciente p WHERE p.activo = :activo")
    List<Paciente> listar(@Param("activo") boolean activo);

    @Query("SELECT p FROM Paciente p WHERE p.dni =: dni")
    Optional<Paciente> buscarPorDni(@Param("dni") String dni);

    @Query("SELECT p FROM Paciente p WHERE " +
            "(:#{#filtro.nombre} is null or p.nombre like %:#{#filtro.nombre}%) and " +
            "(:#{#filtro.apellido} is null or p.apellido like %:#{#filtro.apellido}%) and " +
            "(:#{#filtro.dni} is null or p.dni like %:#{#filtro.dni}%) and " +
            "(:#{#filtro.email} is null or p.email like %:#{#filtro.email}%) and " +
            "(:#{#filtro.activo} is null or p.activo = :#{#filtro.activo})"
    )
    List<Paciente> buscarPorFiltro(@Param("filtro") FiltroUsuario usuario);

}
