package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Oferta {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    @Enumerated(EnumType.STRING)
    private TipoConsultaEnum tipo;
    
    private String detalle;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Enumerated(EnumType.STRING)
    private HorarioEnum horario;
    
    private String ubicacion;
    
    private Double precio;

    private Boolean reservado = false;

    @ManyToOne
    private Profesional profesional;

    @OneToOne(
        mappedBy = "oferta",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Turno turno;

}
