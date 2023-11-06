package com.GrupoD.AppServSalud.dominio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GrupoD.AppServSalud.dominio.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
  
}
