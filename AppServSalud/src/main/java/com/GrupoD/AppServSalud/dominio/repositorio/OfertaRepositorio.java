package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepositorio extends JpaRepository<Oferta, String> {
 
    @Query("SELECT o FROM Oferta o WHERE o.tipo = :tipo")
    List<Oferta> buscarPorTipo(@Param("tipo") TipoConsultaEnum tipo);
    
    @Query("SELECT o FROM Oferta o WHERE o.ubicacion = :ubicacion")
    List<Oferta> buscarPorUbucacion(@Param("ubicacion") String ubicacion);
    
    @Query("SELECT o FROM Oferta o WHERE o.horario = :horario")
    List<Oferta> buscarPorHorario(@Param("horario") HorarioEnum horario);
    
}
