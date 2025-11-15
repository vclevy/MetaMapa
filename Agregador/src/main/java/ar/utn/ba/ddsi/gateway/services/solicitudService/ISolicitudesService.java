package ar.utn.ba.ddsi.gateway.services.solicitudService;

import ar.utn.ba.ddsi.gateway.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.SolicitudOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.Solicitud;

import java.util.List;

public interface ISolicitudesService {
    void registrarSolicitud(SolicitudInputDTO solicitudInputDTO);
    void aprobarSolicitud(Long idSolicitud, String usuarioModificador);
    void rechazarSolicitud(Long idSolicitud, String usuarioModificador);
    boolean verificacionDeSpam(Solicitud unaSolicitud);
    boolean justificacionTieneLongitudValida(String unaJustificacion);
    List<SolicitudOutputDTO> obtenerSolicitudes();
    List<SolicitudOutputDTO> obtenerSolicitudesPendientes();
    boolean tieneSolicitudAprobada(List<Hecho> hechos);
}
