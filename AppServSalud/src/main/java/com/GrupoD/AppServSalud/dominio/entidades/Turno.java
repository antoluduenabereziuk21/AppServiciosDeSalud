/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author antolube20
 */
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

    private Boolean estado;
    
    @ManyToOne
    private Profesional profesional;

    @ManyToOne
    private Paciente paciente;
    
    public Turno(){
        this.estado = true;
    }
    

}
