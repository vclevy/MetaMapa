package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;

public interface ISolcitudesService {
    void registrarSolicitud(SolicitudInputDTO solicitudInputDTO, Usuario unUsuario);
    void cambiarEstadoDeSolicitud(Long idSolicitud, Usuario usuarioModificador, String unaAccion);
    boolean verificacionDeSpam(Solicitud unaSolicitud);
    Long definirId();
    boolean justificacionTieneLongitudValida(String unaJustificacion);
    void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador);
}
