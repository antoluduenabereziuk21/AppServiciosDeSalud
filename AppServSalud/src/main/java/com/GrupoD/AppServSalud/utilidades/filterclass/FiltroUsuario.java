package com.GrupoD.AppServSalud.utilidades.filterclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroUsuario {

    private String nombre;

    private String apellido;

    private String dni;

    private String email;

    private boolean activo;
}
