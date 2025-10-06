package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.AccionesSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;

import java.util.List;

public interface ISolicitudesService {
    void registrarSolicitud(SolicitudInputDTO solicitudInputDTO);
    void cambiarEstadoDeSolicitud(Long idSolicitud, String usuarioModificador, AccionesSolicitud unaAccion);
    boolean verificacionDeSpam(Solicitud unaSolicitud);
    boolean justificacionTieneLongitudValida(String unaJustificacion);
    List<Solicitud> obtenerSolicitudes();
}
