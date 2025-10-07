package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.SolicitudOutputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;

import java.util.List;

public interface ISolicitudesService {
    void registrarSolicitud(SolicitudInputDTO solicitudInputDTO);
    void aprobarSolicitud(Long idSolicitud, String usuarioModificador);
    void rechazarSolicitud(Long idSolicitud, String usuarioModificador);
    boolean verificacionDeSpam(Solicitud unaSolicitud);
    boolean justificacionTieneLongitudValida(String unaJustificacion);
    List<SolicitudOutputDTO> obtenerSolicitudes();
}
