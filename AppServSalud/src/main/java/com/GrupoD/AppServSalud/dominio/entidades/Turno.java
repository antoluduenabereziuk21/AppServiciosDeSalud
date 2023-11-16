package com.GrupoD.AppServSalud.dominio.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.GrupoD.AppServSalud.utilidades.EstadoTurno;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
   
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date FechaTurno;

    private HorarioEnum horaTurno;

    @Enumerated(EnumType.STRING)
    private EstadoTurno estado;

    private String observaciones;
    
    @ManyToOne
    private Profesional profesional;

    @ManyToOne
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name="oferta_id")
    private Oferta oferta;

}
