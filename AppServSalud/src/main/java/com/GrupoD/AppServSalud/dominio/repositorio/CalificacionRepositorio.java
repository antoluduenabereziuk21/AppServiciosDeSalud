/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author leand
 */
@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String> {
    
}
