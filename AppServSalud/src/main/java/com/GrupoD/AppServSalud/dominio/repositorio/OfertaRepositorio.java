package com.GrupoD.AppServSalud.dominio.repositorio;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroOferta;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        @Query("SELECT o FROM Oferta o WHERE o.profesional.id = :id")
        List<Oferta> buscarPorProfesional(@Param("id") String id);

        @Query("SELECT o FROM Oferta o WHERE " +
                        "(:#{#filtro.idProfesional} is null or o.profesional.id = :#{#filtro.idProfesional}) and " +
                        "(:#{#filtro.apellido} is null or o.profesional.apellido like %:#{#filtro.apellido}%) and " +
                        "(:#{#filtro.especialidad} is null or o.profesional.especialidad = :#{#filtro.especialidad}) and "
                        +
                        "(:#{#filtro.fecha} is null or o.fecha = :#{#filtro.fecha}) and " +
                        "(:#{#filtro.horarios} is null or o.horario in :#{#filtro.horarios}) and " +
                        "(:#{#filtro.reservado} is null or o.reservado = :#{#filtro.reservado})")
        Page<Oferta> buscarPorFiltro(@Param("filtro") FiltroOferta filtro, Pageable pageable);

        @Query("SELECT o.id,o.horario FROM Oferta o WHERE " +
                        "(:#{#filtro.idProfesional} is null or o.profesional.id = :#{#filtro.idProfesional}) and " +
                        "(:#{#filtro.fecha} is null or o.fecha = :#{#filtro.fecha}) and " +
                        "(:#{#filtro.reservado} is null or o.reservado = :#{#filtro.reservado})")
        List<Object[]> buscarPorFiltroSinPage(@Param("filtro") FiltroOferta filtro);

}
