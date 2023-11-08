package com.GrupoD.AppServSalud.dominio.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.Permiso;
import com.GrupoD.AppServSalud.utilidades.PermisosEnum;

@Repository
public interface PermisoRepositorio extends JpaRepository<Permiso,String>{
    
    Optional<Permiso> findByPermiso(PermisosEnum permiso);

}
