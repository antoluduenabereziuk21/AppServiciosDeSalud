package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.OfertaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import com.GrupoD.AppServSalud.utilidades.Validacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    public void crearOferta(String tipoConsulta, String detalleOferta, String fechaConsulta,
            String horarioOferta, String ubicacionOferta, Double precioOferta, String idProfesional)
            throws MiExcepcion {

        Validacion.validarStrings(tipoConsulta, detalleOferta, fechaConsulta, horarioOferta, ubicacionOferta,
                idProfesional);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaOerta = null;
        try {
            fechaOerta = dateFormat.parse(fechaConsulta);
        } catch (ParseException ex) {
            throw new MiExcepcion("Error al parsear la fecha");
        }

        validarFecha(fechaOerta, HorarioEnum.valueOf("_" + horarioOferta + "HS"));

        Profesional profesional = profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional"));

        Oferta oferta = new Oferta();

        oferta.setProfesional(profesional);
        oferta.setTipo(TipoConsultaEnum.valueOf(tipoConsulta));
        oferta.setDetalle(detalleOferta);
        oferta.setPrecio(precioOferta);
        oferta.setUbicacion(ubicacionOferta);
        oferta.setHorario(HorarioEnum.valueOf("_" + horarioOferta + "HS"));
        oferta.setFecha(fechaOerta);

        ofertaRepositorio.save(oferta);
    }

    public List<Oferta> listarOfertasProfesional(String id) {
        return ofertaRepositorio.buscarPorProfesional(id);
    }

    public List<Oferta> listarOferta() {

        return ofertaRepositorio.findAll();
    }

    public List<Oferta> listarPorTipo(TipoConsultaEnum tipo) {

        return ofertaRepositorio.buscarPorTipo(tipo);
    }

    public void validarFecha(Date fecha, HorarioEnum horario) throws MiExcepcion {
        System.out.println("Sacando Horario: " + horario.name().substring(1, 3));
        Date fechaActual = new Date();
        if (fecha.before(fechaActual)) {
            throw new MiExcepcion("La fecha no puede ser anterior a la actual");
        }
        if (fecha.equals(fechaActual)) {
            if (Integer.valueOf(horario.name().substring(1, 3)) < fechaActual.getHours()) {
                throw new MiExcepcion("La hora no puede ser anterior a la actual");
            }
        }
        List<Oferta> ofertas = ofertaRepositorio.findAll();

        boolean ofertaExistente = ofertas.stream()
                .anyMatch(oferta -> oferta.getHorario().equals(horario) && oferta.getFecha().equals(fecha));

        if (ofertaExistente) {
            throw new MiExcepcion("Ya existe una oferta para ese horario");
        }

    }
}
