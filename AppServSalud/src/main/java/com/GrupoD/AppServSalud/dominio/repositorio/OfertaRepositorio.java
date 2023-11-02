package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepositorio {

    @Query("SELECT o FROM Oferta o WHERE o.tipo = :tipo")
    List<Oferta> buscarPorTipo(@Param("tipo") String tipo);
}
