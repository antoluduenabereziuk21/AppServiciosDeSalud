package com.GrupoD.AppServSalud.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "admin")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends Usuario{
     
    
}
