package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;

import java.util.List;

public interface ISolcitudesService {
    public void registrarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
    public Solicitud crearSolicitud(String unaJustificacion, Hecho unHecho);
    public void aprobarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
    public void rechazarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador);
}
