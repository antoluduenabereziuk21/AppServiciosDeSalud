package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Oferta {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    @Enumerated(EnumType.STRING)
    private TipoConsultaEnum tipo;
    
    private String detalle;
    
    @Enumerated(EnumType.STRING)
    private HorarioEnum horario;
    
    private String ubicacion;
    
    private Double precio;

    @ManyToOne
    private Profesional profesional;
}
