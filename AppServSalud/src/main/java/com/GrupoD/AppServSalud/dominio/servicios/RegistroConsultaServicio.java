package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.RegistroConsulta;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
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

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearRegistroDeConsulta(String idProfesional, String idPaciente, String detalleConsulta)
            throws MiExcepcion {

        HistoriaClinica historiaClinica = new HistoriaClinica();

        List<RegistroConsulta> registroConsultas = new ArrayList<RegistroConsulta>();

        Paciente paciente = servicioPaciente.findById(idPaciente);
        Profesional profesional = profesionalServicio.buscarPorId(idProfesional);

        RegistroConsulta registroConsulta = new RegistroConsulta();
        registroConsulta.setProfesional(profesional);
        registroConsulta.setDetalleConsulta(detalleConsulta);

        if (paciente.getHistoriaClinica() == null) {
            registroConsultas.add(registroConsulta);
            historiaClinica.setRegistroConsulta(registroConsultas);
            paciente.setHistoriaClinica(historiaClinica);
        }
            
        if (paciente.getHistoriaClinica().getRegistroConsulta() == null) {
            registroConsultas.add(registroConsulta);
            paciente.getHistoriaClinica().setRegistroConsulta(registroConsultas);
        } else {
            paciente.getHistoriaClinica().getRegistroConsulta().add(registroConsulta);
        }
        pacienteRepositorio.save(paciente);
        System.out.println("REGISTRO AGREGADO");
    }

    @Transactional
    public void modificar(String idProfesional, String detalleConsulta,
            String idRegistroConsulta, String dni) throws MiExcepcion {

    }
}
