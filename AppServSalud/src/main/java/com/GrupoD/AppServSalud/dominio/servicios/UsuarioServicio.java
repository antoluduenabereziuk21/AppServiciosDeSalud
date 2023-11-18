package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Usuario;
import com.GrupoD.AppServSalud.dominio.repositorio.UsuarioRepositorio;
import com.GrupoD.AppServSalud.utilidades.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @Override
    public UserDetails loadUserByUsername(String email) throws AuthenticationException {

        Optional<Usuario> userOptional = usuarioRepositorio.buscarPorEmail(email);
        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();

            if (!user.getActivo()) {
                throw new AuthenticationException("Usuario inactivo") {};
            }

            List<GrantedAuthority> autorizaciones = new ArrayList<>();

            GrantedAuthority autorizacion = new SimpleGrantedAuthority("ROLE_" + user.getRol().name());
            autorizaciones.add(autorizacion);

            if (user.getPermisos() != null && !user.getPermisos().isEmpty()) {
                user.getPermisos().forEach(
                        permiso -> autorizaciones.add(new SimpleGrantedAuthority("ROLE_"+permiso.getPermiso().name())));
            }
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuario", user);
            session.setAttribute("nombre", user.getNombre());
            session.setAttribute("role", "ROLE_" + user.getRol().name());

            return new User(user.getEmail(), user.getPassword(), autorizaciones);
        }else{
            throw new AuthenticationException("Usuario no encontrado") {};
        }
    }

    public void createSuperAdminUser(String email, String contrasenha, String nombre, String apellido, String role) {

        Usuario usuario = Usuario.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(contrasenha))
                .nombre(nombre)
                .apellido(apellido)
                .rol(RolEnum.valueOf(role))
                .activo(true)
                .build();
                
        usuarioRepositorio.save(usuario);
    }

    public Usuario getUsuario(String email){
        Optional<Usuario> respuesta = usuarioRepositorio.buscarPorEmail(email);
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }

}
