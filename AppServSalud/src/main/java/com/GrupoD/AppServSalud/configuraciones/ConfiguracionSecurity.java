package com.GrupoD.AppServSalud.configuraciones;

import com.GrupoD.AppServSalud.dominio.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ConfiguracionSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                /* en este ant mach van todos las rutas de los archivos que usamos para el proyecto */
                .antMatchers("/css/*", "/js/*", "/img/*","/assets/**","/styles/**").permitAll()
                /* desde aqui colocaremos las rutas para configurarlas */
                .antMatchers(HttpMethod.GET, "/","/paciente/registro").permitAll()
                .antMatchers(HttpMethod.POST, "/paciente/registro").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws
            Exception{
        auth.userDetailsService(usuarioServicio).
                passwordEncoder(new BCryptPasswordEncoder());
    }

}
