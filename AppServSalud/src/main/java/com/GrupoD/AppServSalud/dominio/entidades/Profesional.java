package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.ObraSocialEnum;
import java.util.List;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profesionales")
public class Profesional {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    @Column(unique = true)
    private String mp;
    
    @Enumerated(EnumType.STRING)
    private EspecialidadEnum especialidad;
    
    private String descripcion;
    
    //private Oferta oferta;
    
    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obrasSociales;
}
