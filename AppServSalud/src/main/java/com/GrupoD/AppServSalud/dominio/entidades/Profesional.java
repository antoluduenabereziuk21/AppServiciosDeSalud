package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.ObraSocialEnum;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profesional extends Usuario{

        
    @Column(unique = true)
    private String matriculaProfesional;
    
    @Enumerated(EnumType.STRING)
    private EspecialidadEnum especialidad;
    
    private String descripcion;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "profesional_turno",
        joinColumns = @JoinColumn(name = "id_profesional"),
        inverseJoinColumns = @JoinColumn(name = "id_turno"))
    private List<Turno> turnos;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "profesional_calificacion",
        joinColumns = @JoinColumn(name = "id_profesional"),
        inverseJoinColumns = @JoinColumn(name = "id_calificacion"))
    private List<Calificacion> calificaciones;
    
    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obrasSociales;
}
