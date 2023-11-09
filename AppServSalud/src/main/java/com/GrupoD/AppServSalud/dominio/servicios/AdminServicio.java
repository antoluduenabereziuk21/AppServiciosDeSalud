package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Admin;
import com.GrupoD.AppServSalud.dominio.entidades.Permiso;
import com.GrupoD.AppServSalud.dominio.repositorio.AdminRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PermisoRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GrupoD.AppServSalud.utilidades.PermisosEnum;
import com.GrupoD.AppServSalud.utilidades.RolEnum;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AdminServicio {
   
    @Autowired
    private AdminRepositorio adminRepositorio;

    @Autowired
    private PermisoRepositorio permisoRepositorio;

    public Admin buscarPorEmail(String email){
        return adminRepositorio.buscarPorEmail(email).get();
    }
    
    @Transactional
    public void crearAdmin(String email, String password, String nombre, String apellido, String role,List<String> permisos){
        try {
            Validacion.validarStrings(nombre, apellido, email, password);
        } catch (MiExcepcion ex) {
            Logger.getLogger(AdminServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Permiso> permisosAdmin = permisos.stream().map(
            permiso -> permisoRepositorio.findByPermiso(PermisosEnum.valueOf(permiso)).get()
        ).collect(Collectors.toList());
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(new BCryptPasswordEncoder().encode(password));
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setRol(RolEnum.valueOf(role));
        admin.setActivo(true);
        admin.setPermisos(permisosAdmin);
        adminRepositorio.save(admin);
    }
   
    @Transactional
    public void modficarDatosAdmin(String idAdmin,String email, String password, String nombre, String apellido, String role,List<String> permisos) throws MiExcepcion{

        List<Permiso> permisosAdmin = permisos.stream().map(
            permiso -> permisoRepositorio.findByPermiso(PermisosEnum.valueOf(permiso)).get()
        ).collect(Collectors.toList());

        Validacion.validarStrings(nombre, apellido, email, password);
        Optional<Admin> respuestaAdmin= adminRepositorio.findById(idAdmin);
        if (respuestaAdmin.isPresent()){
            Admin admin = respuestaAdmin.get();
            admin.setEmail(email);
            admin.setPassword(password);
            admin.setNombre(nombre);
            admin.setRol(RolEnum.valueOf(role));
            admin.setPermisos(permisosAdmin);
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
