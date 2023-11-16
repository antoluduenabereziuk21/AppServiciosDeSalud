package com.GrupoD.AppServSalud.dominio.entidades;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
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
     
    @ManyToOne
    private Profesional profesional;
    
    @Embedded
    private List<RegistroConsulta> registroConsulta = new ArrayList<RegistroConsulta>();
}
