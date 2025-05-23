package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;

import java.util.List;

public interface ISolcitudesService {
    public void registrarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
    public void procesarSolicitudes(List<Solicitud> unaSolicitudes, Usuario usuarioModificador);
}
