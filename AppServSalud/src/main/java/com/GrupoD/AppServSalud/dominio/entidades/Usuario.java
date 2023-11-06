package com.GrupoD.AppServSalud.dominio.entidades;

import com.GrupoD.AppServSalud.utilidades.RolEnum;
import com.GrupoD.AppServSalud.utilidades.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
@DiscriminatorColumn(name = "tipo_usuario")
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true,length = 45)
    private String email;

    private String password;

    private String nombre;

    private String apellido;

    @Column(unique = true,length = 8)
    private String dni;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    private String telefono;

    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private Boolean activo;

    @OneToOne
    private Imagen imagen;

}
