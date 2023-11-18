package com.GrupoD.AppServSalud.utilidades.filterclass;

import java.util.Date;

import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;

import lombok.Data;

@Data
public class FiltroOferta {
    private String idProfesional;

    private String apellido;

    private EspecialidadEnum especialidad;

    private Date fecha;

    private Date fechaSiguiente;

    private Boolean reservado;

    private HorarioEnum[] horarios;
}
