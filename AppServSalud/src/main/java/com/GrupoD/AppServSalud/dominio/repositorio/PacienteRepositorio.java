package com.GrupoD.AppServSalud.dominio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.GrupoD.AppServSalud.entidades.Paciente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String>{
    
    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    public Paciente buscarPorDni(@Param("dni")String dni);
}
