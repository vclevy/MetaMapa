package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;

public interface ISolcitudesService {
    public void registrarSolicitud(String unaJustificacion, Long idHecho, Usuario unUsuario);
    public void cambiarEstadoDeSolicitud(Long idSolicitud, Usuario usuarioModificador, String unaAccion);
    public boolean verificacionDeSpam(Solicitud unaSolicitud);
    public Long definirId();
    public boolean justificacionTieneLongitudValida(String unaJustificacion);
    public void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador);
}
