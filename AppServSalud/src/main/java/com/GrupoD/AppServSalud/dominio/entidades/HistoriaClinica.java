/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.GrupoD.AppServSalud.dominio.entidades;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

/**
 *
 * @author antolube20
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "historia_clinica")

public class HistoriaClinica {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String historia;
     
    @ManyToMany
    @JoinTable(name = "historia_clinica_profesional",
            joinColumns = @JoinColumn(name = "id_historia_clinica"),
            inverseJoinColumns = @JoinColumn(name = "id_profesional"))
    private List<Profesional> profesional;

    @ManyToMany
    @JoinTable(name = "historia_clinica_paciente",
            joinColumns = @JoinColumn(name = "id_historia_clinica"),
            inverseJoinColumns = @JoinColumn(name = "id_paciente"))
    private List<Paciente> paciente;
}
