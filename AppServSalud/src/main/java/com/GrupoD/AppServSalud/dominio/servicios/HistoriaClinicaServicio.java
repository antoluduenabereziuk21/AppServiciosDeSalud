package com.GrupoD.AppServSalud.dominio.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.HistoriaClinicaRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.Validacion;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    HistoriaClinicaRepositorio historiaClinicaRepositorio;
    
    @Transactional
    public void crearHistoriaClinica (String historia, Paciente paciente, List<Profesional> profesional) throws MiExcepcion{
        Validacion.validarStrings(historia);

        if (paciente == null) {
            throw new MiExcepcion("El paciente no puede ser Nulo");
        }

        if (profesional == null) {
            throw new MiExcepcion("El profesional no puede ser Nulo");
        }

        HistoriaClinica hClinica = new HistoriaClinica();

        hClinica.setHistoria(historia);
        hClinica.setPaciente(paciente);
        hClinica.setProfesional(profesional);

        historiaClinicaRepositorio.save(hClinica);
    }

    @Transactional
    public void modificarHistoriaClinica (String mailPaciente, String historia, List<Profesional> profesional, Paciente paciente ){

        Optional<HistoriaClinica> historiaRespuesta = historiaClinicaRepositorio.buscarPorPaciente(mailPaciente);

        if (historiaRespuesta.isPresent()) {
            HistoriaClinica historiaClinica = historiaRespuesta.get();

            historiaClinica.setHistoria(historia);
            historiaClinica.setProfesional(profesional);
            historiaClinica.setPaciente(paciente);

            historiaClinicaRepositorio.save(historiaClinica);
        }
        
    }
}
