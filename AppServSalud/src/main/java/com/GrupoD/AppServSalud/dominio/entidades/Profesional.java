package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.ObraSocialEnum;
import java.util.List;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profesionales")
public class Profesional extends Usuario{

        
    @Column(unique = true)
    private String matriculaProfesional;
    
    @Enumerated(EnumType.STRING)
    private EspecialidadEnum especialidad;
    
    private String descripcion;
    
    //@OneToMany
    //private List<Oferta> oferta;
    
    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obrasSociales;
}
