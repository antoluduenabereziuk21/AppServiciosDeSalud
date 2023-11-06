package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import com.GrupoD.AppServSalud.dominio.repositorio.AdminRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GrupoD.AppServSalud.utilidades.RolEnum;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AdminServicio {
   
    @Autowired
    private AdminRepositorio adminRepositorio;


    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Transactional
    public void crearAdmin(String email, String password, String nombre, String apellido, String role){
        try {
            Validacion.validarStrings(nombre, apellido, email, password);
        } catch (MiExcepcion ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuarioServicio.createAdminUser(email,password,nombre,apellido,role);
    }
   
    @Transactional

    public void modficarDatosAdmin(String idAdmin,String email, String password, String nombre, String apellido, String role) throws MiExcepcion{
        Validacion.validarStrings(nombre, apellido, email, password);
        Optional<Admin> respuestaAdmin= adminRepositorio.findById(idAdmin);
        if (respuestaAdmin.isPresent()){
            Admin admin = respuestaAdmin.get();
            admin.setEmail(email);
            admin.setPassword(password);
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
    /*
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarActivos();
    }
    @Transactional(readOnly = true)
    public List<Admin> listarAdministradoresActivos(){
        return adminRepositorio.buscarInctivos();
    }*/
   
}
