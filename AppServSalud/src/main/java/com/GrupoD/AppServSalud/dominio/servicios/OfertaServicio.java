package com.GrupoD.AppServSalud.dominio.servicios;

import com.GrupoD.AppServSalud.dominio.entidades.Oferta;
import com.GrupoD.AppServSalud.dominio.entidades.Paciente;
import com.GrupoD.AppServSalud.dominio.entidades.Profesional;
import com.GrupoD.AppServSalud.dominio.repositorio.OfertaRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.PacienteRepositorio;
import com.GrupoD.AppServSalud.dominio.repositorio.ProfesionalRepositorio;
import com.GrupoD.AppServSalud.excepciones.MiExcepcion;
import com.GrupoD.AppServSalud.utilidades.EspecialidadEnum;
import com.GrupoD.AppServSalud.utilidades.HorarioEnum;
import com.GrupoD.AppServSalud.utilidades.TipoConsultaEnum;
import com.GrupoD.AppServSalud.utilidades.Validacion;
import com.GrupoD.AppServSalud.utilidades.filterclass.FiltroOferta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OfertaServicio {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    public void crearOferta(String tipoConsulta, String detalleOferta, String fechaConsulta,
            String horarioOferta, String ubicacionOferta, Double precioOferta, String idProfesional)
            throws MiExcepcion {

        Validacion.validarStrings(tipoConsulta, detalleOferta, fechaConsulta, horarioOferta, ubicacionOferta,
                idProfesional);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaOerta = null;
        System.out.println(horarioOferta);
        try {
            fechaOerta = dateFormat.parse(fechaConsulta);
        } catch (ParseException ex) {
            throw new MiExcepcion("Error al parsear la fecha");
        }

        validarFecha(fechaOerta, HorarioEnum.valueOf("HORARIO_" + horarioOferta + "_00_HS"), idProfesional);

        Profesional profesional = profesionalRepositorio.findById(idProfesional)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional"));

        Oferta oferta = new Oferta();

        oferta.setProfesional(profesional);
        oferta.setTipo(TipoConsultaEnum.valueOf(tipoConsulta));
        oferta.setDetalle(detalleOferta);
        oferta.setPrecio(precioOferta);
        oferta.setUbicacion(ubicacionOferta);
        try{
            oferta.setHorario(HorarioEnum.valueOf("HORARIO_" + horarioOferta + "_00_HS"));
        }catch (IllegalArgumentException e){
            throw new MiExcepcion("El horario no es valido");
        }
        oferta.setFecha(fechaOerta);
        oferta.setReservado(false);

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

    public void validarFecha(Date fecha, HorarioEnum horario, String idProfesional) throws MiExcepcion {
        Date fechaActual = new Date();
        if (fecha.before(fechaActual)) {
            throw new MiExcepcion("La fecha no puede ser anterior a la actual");
        }
        if (fecha.equals(fechaActual)) {
            if (Integer.valueOf(horario.name().substring(1, 3)) < fechaActual.getHours()) {
                throw new MiExcepcion("La hora no puede ser anterior a la actual");
            }
        }
        List<Oferta> ofertas = listarOfertasProfesional(idProfesional);

        boolean ofertaExistente = ofertas.stream()
                .anyMatch(oferta -> oferta.getHorario().equals(horario) && oferta.getFecha().equals(fecha));

        if (ofertaExistente) {
            throw new MiExcepcion("Ya existe una oferta para ese horario");
        }

    }

    @Transactional
    public void reservarOferta(String idOferta, String idPaciente) throws MiExcepcion {

        Validacion.validarStrings(idOferta, idPaciente);

        Oferta oferta = ofertaRepositorio.findById(idOferta)
                .orElseThrow(() -> new MiExcepcion("No se encontro ninguna oferta"));

        if (oferta.getReservado()) {
            throw new MiExcepcion("El turno Ya no se encuentra disponible");
        }

        Paciente paciente = pacienteRepositorio.findById(idPaciente)
                .orElseThrow(() -> new MiExcepcion("No se encontro ningun paciente"));


        turnoServicio.crearTurno(oferta,paciente);

        oferta.setReservado(true);
        ofertaRepositorio.save(oferta);

        notificacionServicio.crearNotificacionPedidoTurno(idPaciente, oferta.getProfesional().getId());

    }

    /**
     * Metodo para crear una oferta por rango de horarios
     * 
     * @param fecha         Fecha de la oferta
     * @param desde         Horario desde String
     * @param hasta         Horario hasta String
     * @param intervalo     Intervalo de tiempo (1 = 1 hora, 15 = 15 minutos, 30 =
     *                      30 minutos)
     * @param tipoConsulta  Tipo de consulta(PRESENCIAL, TELEMEDICINA)
     * @param detalle       Detalle de la consulta
     * @param ubicacion     Ubicacion de la consulta
     * @param precio        Precio de la consulta
     * @param idProfesional Id del profesional
     */
    @Transactional
    public void crearOfertaPorRango(Date fecha, String desde, String hasta,
            Integer intervalo, String tipoConsulta, String detalle, String ubicacion,
            Double precio, String idProfesional) throws MiExcepcion {

        Validacion.validarDate(fecha);
        Validacion.validarStrings(desde, hasta);
        Validacion.validarInteger(intervalo);
        if (Integer.valueOf(desde) > Integer.valueOf(hasta))
            throw new MiExcepcion("El horario desde no puede ser mayor al horario hasta");

        if (intervalo != 1 && intervalo != 15 && intervalo != 30)
            throw new MiExcepcion("El intervalo de tiempo debe ser 1, 15 o 30 minutos");
        if (Integer.valueOf(desde) < 0 || Integer.valueOf(desde) > 23 || Integer.valueOf(hasta) < 0
                || Integer.valueOf(hasta) > 23)
            throw new MiExcepcion("El horario debe estar entre 0 y 23 horas");

        List<Oferta> listaOfertas = new ArrayList<>();
        int incremento = intervalo == 1 ? 60 : (intervalo == 15 ? 15 : 30);

        for (int i = Integer.valueOf(desde); i <= Integer.valueOf(hasta); i++) {
            for (int j = 0; j < 60; j += incremento) {
                Oferta oferta = new Oferta();
                oferta.setFecha(fecha);
                if (i < 10) {
                    if (j == 0) {
                        oferta.setHorario(HorarioEnum.valueOf("HORARIO_0" + i + "_00_HS"));
                    } else {
                        oferta.setHorario(HorarioEnum.valueOf("HORARIO_0" + i + "_" + j + "_HS"));
                    }
                } else {
                    if (j == 0) {
                        oferta.setHorario(HorarioEnum.valueOf("HORARIO_" + i + "_00_HS"));
                    } else {
                        oferta.setHorario(HorarioEnum.valueOf("HORARIO_" + i + "_" + j + "_HS"));
                    }
                }
                oferta.setTipo(TipoConsultaEnum.valueOf(tipoConsulta));
                oferta.setDetalle(detalle);
                oferta.setUbicacion(ubicacion);
                oferta.setPrecio(precio);
                oferta.setProfesional(profesionalRepositorio.findById(idProfesional)
                        .orElseThrow(() -> new MiExcepcion("No se encontro ningun profesional")));
                listaOfertas.add(oferta);
            }
        }

        try {
            validarListaDeTurnos(listaOfertas, idProfesional);
            ofertaRepositorio.saveAll(listaOfertas);
        } catch (MiExcepcion e) {
            throw new MiExcepcion(e.getMessage());
        }
    }

    private void validarListaDeTurnos(List<Oferta> listaOfertas, String idProfesional) throws MiExcepcion {
        List<Oferta> ofertas = ofertaRepositorio.buscarPorProfesional(idProfesional);
        for (Oferta oferta : listaOfertas) {
            boolean ofertaExistente = ofertas.stream()
                    .anyMatch(ofertaAux -> ofertaAux.getHorario().equals(oferta.getHorario())
                            && ofertaAux.getFecha().equals(oferta.getFecha()));
            if (ofertaExistente) {
                throw new MiExcepcion("Ya existe una oferta para ese horario");
            }
        }
    }

    public List<EspecialidadEnum> listarEspecialidades() {
        List<EspecialidadEnum> especialidades = new ArrayList<>();
        for (EspecialidadEnum especialidad : EspecialidadEnum.values()) {
            especialidades.add(especialidad);
        }
        return especialidades;
    }

    public void eliminarTodos() {
        ofertaRepositorio.deleteAll();
    }

    public Page<Oferta> listarOfertas(Pageable pageable) {
        return ofertaRepositorio.findAll(pageable);
    }

    public Page<Oferta> listarOfertas(Pageable pageable, String apellido, String especialidad,
            String fecha, String desde, String hasta) throws MiExcepcion {
        
        Date fechaOerta = null;
        
        if (fecha != null && !fecha.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fechaOerta = dateFormat.parse(fecha);
            } catch (ParseException ex) {
            }
        }

        FiltroOferta filtro = new FiltroOferta();
        filtro.setReservado(false);
        filtro.setApellido(apellido);
        if(especialidad != null && !especialidad.isEmpty()){
            filtro.setEspecialidad(EspecialidadEnum.valueOf(especialidad));
        }
        filtro.setFecha(fechaOerta);
        if(desde != null && !desde.isEmpty() && hasta != null && !hasta.isEmpty()){
            filtro.setHorarios(crearHorarios(Integer.valueOf(desde), Integer.valueOf(hasta)));
        }
        return ofertaRepositorio.buscarPorFiltro(filtro, pageable);
    }

    private HorarioEnum[] crearHorarios(Integer desde, Integer hasta) {
        HorarioEnum[] horarios = new HorarioEnum[(hasta-desde)*4];
        int aux = 0;
        int inc = desde;
        for (int i = 0; i < (hasta-desde)*4; i+=4) {
            for (int j = 0; j < 60; j += 15) {
                if (inc < 10) {
                    if (j == 0) {
                        horarios[i+aux] = HorarioEnum.valueOf("HORARIO_0" + (inc) + "_00_HS");
                    } else {
                        horarios[i+aux] = HorarioEnum.valueOf("HORARIO_0" + (inc) + "_" + j + "_HS");
                    }
                } else {
                    if (j == 0) {
                        horarios[i+aux] = HorarioEnum.valueOf("HORARIO_" + (inc) + "_00_HS");
                    } else {
                        horarios[i+aux] = HorarioEnum.valueOf("HORARIO_" + (inc) + "_" + j + "_HS");
                    }
                }
                aux++;
            }
            inc++;
            aux = 0;
        }
        System.out.println(Arrays.toString(horarios));
        return horarios;
    }

    public List<Object[]> listarOfertasPorFecha(Date fecha,String idProfesional) {
        FiltroOferta filtro = new FiltroOferta();
        filtro.setFecha(fecha);
        filtro.setReservado(false);
        filtro.setIdProfesional(idProfesional);
        return ofertaRepositorio.buscarPorFiltroSinPage(filtro);
    }

}
