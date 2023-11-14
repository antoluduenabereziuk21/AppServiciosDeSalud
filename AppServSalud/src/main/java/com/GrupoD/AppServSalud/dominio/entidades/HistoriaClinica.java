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
    @JoinTable(name = "historia_clinica_profesional",
            joinColumns = @JoinColumn(name = "id_historia_clinica"),
            inverseJoinColumns = @JoinColumn(name = "id_profesional"))
    private List<Profesional> profesional;

    @ManyToOne
    @JoinTable(name = "historia_clinica_paciente",
            joinColumns = @JoinColumn(name = "id_historia_clinica"),
            inverseJoinColumns = @JoinColumn(name = "id_paciente"))
    private Paciente paciente;

//     @OneToMany
//     private List<RegistroConsulta>;
}
