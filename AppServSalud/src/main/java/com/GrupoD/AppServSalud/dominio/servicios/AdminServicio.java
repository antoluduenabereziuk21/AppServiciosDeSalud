/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antolube20
 */
@Service
public class AdminServicio {
   
    @Autowired
    private AdminRepositorio adminRepositorio;


    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Transactional
    public void crearAdmin(String email, String contrasenha, String nombre, String apellido, String role){
        try {
            Validacion.validarStrings(nombre, apellido, email, contrasenha);
        } catch (MiExcepcion ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuarioServicio.createAdminUser(email,contrasenha,nombre,apellido,role);
    }
   
    @Transactional

    public void modficarDatosAdmin(String idAdmin,String email, String contrasenha, String nombre, String apellido, String role){
        Validacion.validarStrings(nombre, apellido, email, contrasenha);
        Optional<Admin> respuestaAdmin= adminRepositorio.findById(idAdmin);
        if (respuestaAdmin.isPresent()){
            Admin admin = respuestaAdmin.get();
            admin.setEmail(email);
            admin.setContresenha(email);
            admin.setNombre(nombre);
            admin.setRol(RolEnum.valueOf(role));

             adminRepositorio.save(admin);
        }

    }

    @Transactional
    public void modificarEstadoAdmin(boolean enable,String idAdmin){
        Optional<Admin> respuesta = adminRepositorio.findById(idAdmin);

        if(respuesta.isPresent()){

            Admin admin = respuesta.get();

            admin.setActivo(enable);

            adminRepositorio.save(admin);
        }
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradores(){
        return adminRepositorio.findAll();
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarActivos();
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarInctivos();
    }
   
}
