package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.HistoriaClinica;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.entidades.RegistroConsulta;
import com.GrupoD.AppServSalud.dominio.repositorio.HistoriaClinicaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;

import java.util.List;
import java.time.LocalDateTime;

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

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Transactional
    public void crearRegistroDeConsulta(String idProfesional, String idPaciente, String detalleConsulta)
            throws MiExcepcion {

        Paciente paciente = servicioPaciente.findById(idPaciente);
        Profesional profesional = profesionalServicio.buscarPorId(idProfesional);

        // Verifica si el paciente ya tiene una historia clínica
        HistoriaClinica historiaClinica = paciente.getHistoriaClinica();
        if (historiaClinica == null) {
            historiaClinica = new HistoriaClinica();
            paciente.setHistoriaClinica(historiaClinica);
            historiaClinica.setPaciente(paciente);
            historiaClinica.setProfesional(profesional);
        }

        // Crea un nuevo registro de consulta
        RegistroConsulta registroConsulta = new RegistroConsulta();
        registroConsulta.setProfesional(profesional);
        registroConsulta.setDetalleConsulta(detalleConsulta);
        registroConsulta.setFechaConsulta(LocalDateTime.now());

        // Asocia el registro de consulta con la historia clínica y viceversa
        registroConsulta.setHistoriaClinica(historiaClinica);
        historiaClinica.getRegistrosConsultas().add(registroConsulta);

        // Guarda los cambios en el paciente, lo que también persistirá la historia
        // clínica y el registro de consulta
        pacienteRepositorio.save(paciente);

        System.out.println("REGISTRO AGREGADO");
    }

    @Transactional
    public void modificar(String idProfesional, String detalleConsulta,
            String idRegistroConsulta, String dni) throws MiExcepcion {

    }

    public List<Paciente> findPacientesConHistoriaClinicaByProfesional(String profesionalId) {
        return historiaClinicaRepositorio.findPacientesConHistoriaClinicaByProfesional(profesionalId);
    }

    public List<HistoriaClinica> findHistoriasClinicasByProfesional(String profesionalId) {
        return historiaClinicaRepositorio.findHistoriasClinicasByProfesional(profesionalId);
    }

    public List<RegistroConsulta> findRegistrosByPacienteAndProfesional(String pacienteId, String profesionalId) {
        return historiaClinicaRepositorio.findByPacienteAndProfesional(pacienteId, profesionalId);
    }
    

}
