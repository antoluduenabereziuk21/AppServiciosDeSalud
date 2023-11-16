package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.RegistroConsulta;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroConsultaServicio {

    @Autowired
    private ServicioPaciente servicioPaciente;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Transactional
    public void crearRegistroDeConsulta(String idProfesional, String idPaciente, String detalleConsulta)
            throws MiExcepcion {
        Paciente paciente = servicioPaciente.findById(idPaciente);
        Profesional profesional = profesionalServicio.buscarPorId(idProfesional);
        RegistroConsulta registroConsulta = new RegistroConsulta();
        registroConsulta.setProfesional(profesional);

        registroConsulta.setDetalleConsulta(detalleConsulta);
        if (paciente.getHistoriaClinica().getRegistroConsulta() != null) {
            paciente.getHistoriaClinica().getRegistroConsulta().add(registroConsulta);
        } else {
            List<RegistroConsulta> registroConsultas = new ArrayList();
            registroConsultas.add(registroConsulta);
            paciente.getHistoriaClinica().
        }
    }

    @Transactional
    public void modificar(String idProfesional, String detalleConsulta,
            String idRegistroConsulta, String dni) throws MiExcepcion {

    }
}
