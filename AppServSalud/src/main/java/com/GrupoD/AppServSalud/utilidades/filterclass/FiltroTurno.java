package com.GrupoD.AppServSalud.utilidades.filterclass;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.utilidades.EstadoTurno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroTurno {
    private Paciente paciente;

    private Profesional profesional;

    private Oferta oferta;

    private EstadoTurno[] estados;
}
