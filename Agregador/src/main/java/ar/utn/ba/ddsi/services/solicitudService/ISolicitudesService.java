package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;

import java.util.List;

public interface ISolicitudesService {
    void registrarSolicitud(SolicitudInputDTO solicitudInputDTO, Long idUsuario);
    void cambiarEstadoDeSolicitud(Long idSolicitud, Long idUsuarioModificador, String unaAccion);
    boolean verificacionDeSpam(Solicitud unaSolicitud);
    boolean justificacionTieneLongitudValida(String unaJustificacion);
    void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador);
    List<Solicitud> obtenerSolicitudes();
}
