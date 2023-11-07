package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import java.util.Optional;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepositorio extends JpaRepository<Admin, String> {

    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Optional<Admin> buscarPorEmail(@Param("email") String email);

    //modificar estaba mal escrito
   /* @Query("Update a FROM Admin a WHERE a.email = :email AND a.contrasenha = :contrasenha AND a.nombre = :nombre AND a.apellido = :apellido AND a.role = :role")
    void modficarDatosAdmin(@Param("email") String email, @Param("contrasenha") String contrasenha, @Param("nombre") String nombre, @Param("apellido") String apellido, @Param("role") RolEnum role);

    @Query("SELECT a FROM Admin a WHERE a.activo = true")
    List<Admin> listarAdministradoresActivos();

    @Query("SELECT a FROM Admin a WHERE a.activo = false")
    List<Admin> listarAdministradoresInactivos();*/
   // Falta el que modifica el estado del admin. 

}
