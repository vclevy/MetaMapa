package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;

public interface ISolcitudesService {
    public void registrarSolicitud(String unaJustificacion, Hecho unHecho, Usuario unUsuario);
    public Solicitud crearSolicitud(String unaJustificacion, Hecho unHecho, Usuario unUsuarioModificador);
    public void aprobarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
    public void rechazarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
}
