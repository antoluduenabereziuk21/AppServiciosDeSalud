package com.GrupoD.AppServSalud.utilidades;

import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

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
    public static void validarStrings(String... args) throws MiExcepcion {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                throw new MiExcepcion("Error: Todos los campos son obligatorios");
            }
        }
    }

    public static void validarDate(Date fecha) throws MiExcepcion {
        if (fecha == null) {
            throw new MiExcepcion("Error: La fecha es obligatoria");
        }
    }

    public static void validarInteger(Integer... nums) throws MiExcepcion{
        for (Integer num : nums) {
            if (num == null || num < 0) {
                throw new MiExcepcion("Error: Todos los campos son obligatorios");
            }
        }
    }

}
