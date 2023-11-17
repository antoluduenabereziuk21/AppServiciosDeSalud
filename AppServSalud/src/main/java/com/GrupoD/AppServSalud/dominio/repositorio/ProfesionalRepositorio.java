/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import java.util.List;
import java.util.Optional;

import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author leand
 */
@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String>{
    
    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    List<Profesional> buscarPorEspecialidad(@Param("especialidad") EspecialidadEnum especialidad);

    
//    @Query("SELECT p FROM Profesional p WHERE p.ubicacion  =: ubicacion")
//    List<Profesional> buscarPorUbicacion(@Param("ubicacion") String ubicacion);

    
    @Query("SELECT p FROM Profesional p WHERE p.nombre =: nombre")
    List<Profesional> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Profesional p WHERE p.nombre =: nombre AND p.especialidad =: especialidad")
    List<Profesional> buscarPorNombreYEspecialidad(@Param("nombre")String nombre, @Param("especialidad") EspecialidadEnum especialidad);

//    @Query("SELECT p FROM Profesional p WHERE p.nombre =: nombre AND p.especialidad =: especialidad AND p.ubicacion =: ubicacion")
//    List<Profesional> buscarPorNombreEspecialidadYUbucacion(@Param("nombre")String nombre,
//            @Param("especialidad")EspecialidadEnum especialidad,@Param("ubicacion") String ubicacion);

    @Query("SELECT p FROM Profesional p WHERE p.activo = :activo")
    List<Profesional> listarTodos(@Param("activo") boolean activo);

    @Query("SELECT p FROM Profesional p WHERE p.email = :email")
    Optional<Profesional> buscarPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Profesional p WHERE " +
            "(:#{#filtro.nombre} is null or p.nombre like %:#{#filtro.nombre}%) and " +
            "(:#{#filtro.apellido} is null or p.apellido like %:#{#filtro.apellido}%) and " +
            "(:#{#filtro.dni} is null or p.dni like %:#{#filtro.dni}%) and " +
            "(:#{#filtro.email} is null or p.email like %:#{#filtro.email}%) and " +
            "(:#{#filtro.activo} is null or p.activo = :#{#filtro.activo})"
    )
    List<Profesional> buscarPorFiltro(@Param("filtro") FiltroUsuario usuario);
       
}
