package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;

public interface ISolcitudesService {
    public void registrarSolicitud(String unaJustificacion, Long idHecho, Usuario unUsuario);
    public void aprobarSolicitud(Long idSolicitud, Usuario usuarioModificador);
    public void rechazarSolicitud(Long idSolicitud, Usuario usuarioModificador);
    public boolean verificacionDeSpam(Solicitud unaSolicitud);
    public Long definirId();
    public boolean justificacionTieneLongitudValida(String unaJustificacion);
    public void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador);
}
