package com.GrupoD.AppServSalud.utilidades;

import com.GrupoD.AppServSalud.excepciones.Excepcion;

import java.util.Date;

public class Validacion {

    public static boolean esNumero(String numero){
        return numero.matches("[0-9]+");
    }

    public static boolean esCadena(String cadena){
        return cadena.matches("[a-zA-Z ]+");
    }

    public static boolean esFecha(String fecha){
        return fecha.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }

    public static boolean esEmail(String email){
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }
    public static void validar(String email, String contrasenha, String nombre, String apellido, String dni,
                        Date fechaDeNacimiento, String sexo, String telefono) throws Excepcion {

        if(email == null || email.isEmpty()){
            throw new Excepcion("Ingrese un email");
        }
        if(contrasenha == null || contrasenha.isEmpty()){

            throw new Excepcion("Ingrese una contraseña");
        }
        if(nombre == null || nombre.isEmpty()){

            throw new Excepcion("Ingrese su nombre");
        }
        if(apellido == null || apellido.isEmpty()){

            throw new Excepcion("Ingrese su apellido");
        }
        if(dni == null || dni.isEmpty()){

            throw new Excepcion("Ingrese su dni");
        }
        if(fechaDeNacimiento == null){

            throw new Excepcion("Ingrese su fecha de nacimiento");
        }
        if(telefono == null || telefono.isEmpty()){

            throw new Excepcion("Ingrese un contacto");
        }
        if(sexo == null){

            throw new Excepcion("Ingrese su sexo");
        }
    }
}
