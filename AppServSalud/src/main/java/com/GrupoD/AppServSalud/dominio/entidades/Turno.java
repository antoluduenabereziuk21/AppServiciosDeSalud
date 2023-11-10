package com.GrupoD.AppServSalud.dominio.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.GrupoD.AppServSalud.utilidades.HorarioEnum;

@Entity
@Data
@Getter
@Setter
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

    private Boolean estado;
    private Boolean activoPaciente;
    private Boolean activoProfesional;
    
    @ManyToOne
    private Profesional profesional;

    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Oferta oferta;

    public Turno(){
        this.estado = true;
    }

}
