package com.GrupoD.AppServSalud.dominio.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.HistoriaClinicaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.Validacion;

@Service
public class HistoriaClinicaServicio {

    @Autowired
    HistoriaClinicaRepositorio historiaClinicaRepositorio;
    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    
    @Transactional
    public void crearHistoriaClinica(String idPaciente, String idProfesional) throws MiExcepcion{

//        Validacion.validarStrings(historia);

        Profesional profesional = profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional"));
        
        Paciente paciente = pacienteRepositorio.findById(idPaciente)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun paciente"));

        

        HistoriaClinica hClinica = new HistoriaClinica();

        hClinica.setPaciente(paciente);
        hClinica.setProfesional(profesional);
//        hClinica.setHistoria(historia);


        historiaClinicaRepositorio.save(hClinica);
    }

    @Transactional
    public void modificarHistoriaClinica (String mailPaciente, Paciente paciente, Profesional profesional ){

        Optional<HistoriaClinica> historiaRespuesta = historiaClinicaRepositorio.buscarPorPaciente(mailPaciente);

        if (historiaRespuesta.isPresent()) {
            HistoriaClinica historiaClinica = historiaRespuesta.get();

//            historiaClinica.setHistoria(historia);
            historiaClinica.setProfesional(profesional);
            historiaClinica.setPaciente(paciente);

            historiaClinicaRepositorio.save(historiaClinica);
        }
        
    }
}
