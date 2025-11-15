package ar.utn.ba.ddsi.gateway.services.solicitudService;

import ar.utn.ba.ddsi.gateway.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.SolicitudOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.gateway.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.gateway.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.gateway.services.spam.DetectorDeSpam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static ar.utn.ba.ddsi.gateway.models.entities.solicitud.EstadoDeSolicitudDeEliminacion.APROBADA;


@Service
public class SolicitudesService implements ISolicitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;
    @Autowired
    private IHechosRepository hechosRepository;
    @Autowired
    private DetectorDeSpam detectorDeSpam;

    @Override
    public void registrarSolicitud(SolicitudInputDTO solicitudInputDTO) {

        if (!this.justificacionTieneLongitudValida(solicitudInputDTO.getJustificacion())) {
            return;
        }

        Hecho hecho = hechosRepository.findById(solicitudInputDTO.getIdHecho())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Hecho no encontrado con id " + solicitudInputDTO.getIdHecho()));

        Solicitud unaSolicitud = new Solicitud();
        unaSolicitud.setJustificacionDeEliminacion(solicitudInputDTO.getJustificacion());
        unaSolicitud.setHecho(hecho);
        unaSolicitud.setFechaDeCargaDeSolicitud(LocalDateTime.now());
        unaSolicitud.setNombreDeUsuario(solicitudInputDTO.getNombreDeUsuario());
        unaSolicitud.setHistorialSolicitud(null);

        if (!this.verificacionDeSpam(unaSolicitud)) {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
            solicitudesRepository.save(unaSolicitud);
        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
        }
        solicitudesRepository.save(unaSolicitud);
    }

    @Override
    public void aprobarSolicitud(Long idSolicitud, String usuarioModificador) {
        cambiarEstado(idSolicitud, usuarioModificador, APROBADA);
        Solicitud solicitud = solicitudesRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada con id " + idSolicitud));
        Hecho hecho = solicitud.getHecho();
        hecho.setFueEliminado(Boolean.TRUE);
        hechosRepository.save(hecho);
    }

    @Override
    public void rechazarSolicitud(Long idSolicitud, String usuarioModificador) {
        cambiarEstado(idSolicitud, usuarioModificador, EstadoDeSolicitudDeEliminacion.RECHAZADA);
    }

    private void cambiarEstado(Long idSolicitud, String usuarioModificador, EstadoDeSolicitudDeEliminacion nuevoEstado) {
        Solicitud solicitud = solicitudesRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada con id " + idSolicitud));

        HistorialSolicitud historialSolicitud = new HistorialSolicitud();
        historialSolicitud.setEstado(solicitud.getEstado());
        historialSolicitud.setNombreDeUsuario(usuarioModificador);
        historialSolicitud.setFechaModificacion(LocalDateTime.now());
        historialSolicitud.setSolicitud(solicitud);

        solicitud.getHistorialSolicitud().add(historialSolicitud);

        solicitud.setEstado(nuevoEstado);
        solicitud.setFechaDeEvaluacionDeSolicitud(LocalDateTime.now());

        solicitudesRepository.save(solicitud);
    }

    @Override
    public boolean verificacionDeSpam(Solicitud unaSolicitud) {
        return this.detectorDeSpam.esSpam(unaSolicitud.getJustificacionDeEliminacion());
    }

    @Override
    public boolean justificacionTieneLongitudValida(String unaJustificacion) {
        return unaJustificacion != null && unaJustificacion.length() >= 500;
    }

    @Override
    public List<SolicitudOutputDTO> obtenerSolicitudes() {
        return solicitudesRepository.findAll().stream()
                .map(SolicitudOutputDTO::new)
                .toList();
    }
    @Override
    public List<SolicitudOutputDTO> obtenerSolicitudesPendientes() {
        return solicitudesRepository.findByEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE).stream()
                .map(SolicitudOutputDTO::new)
                .toList();
    }

    @Override
    public boolean tieneSolicitudAprobada (List<Hecho> hechos) {
        if (hechos == null || hechos.isEmpty()) return false;

        List<Long> ids = hechos.stream()
                .filter(h -> h != null && h.getId() != null)
                .map(Hecho::getId)
                .distinct()
                .toList();

        if (ids.isEmpty()) return false;

        return solicitudesRepository.existsByHechoIdInAndEstado(ids, APROBADA);
    }

}
