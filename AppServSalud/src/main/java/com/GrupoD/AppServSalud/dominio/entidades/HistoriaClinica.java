package com.GrupoD.AppServSalud.dominio.entidades;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HistoriaClinica {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String historia;
     
    @ManyToMany
    private Profesional profesional;

    @ManyToOne
    private Paciente paciente;
    
    @ManyToMany
    @JoinTable(name = "historiaClinica_registroConsulta",
            joinColumns = @JoinColumn(name = "id_historiaClinica"),
            inverseJoinColumns = @JoinColumn(name = "id_registroConsulta"))
    private List<RegistroConsulta> registroConsulta;
}
