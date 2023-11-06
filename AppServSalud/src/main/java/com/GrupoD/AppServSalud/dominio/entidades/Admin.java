package com.GrupoD.AppServSalud.dominio.entidades;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends Usuario{
     
    
}
