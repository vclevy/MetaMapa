package ar.utn.ba.ddsi.models.entities.roles;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.Permisos;
import ar.utn.ba.ddsi.services.solicitudService.ISolcitudesService;
import lombok.Getter;

import java.util.List;

@Getter
public class Usuario {
    private String nombre;
    private IRol rol;

    private final ISolcitudesService solicitudesService;

    public Usuario(ISolcitudesService solcitudesService) {
        this.solicitudesService = solcitudesService;
    }

    public void solicitarEliminacionDeUnHecho(String unaJustificacion, Hecho unHecho) {
        if (rol.tenesPermiso(Permisos.SOLICITAR_ELIMINACION)) {
            // CREO LA SOLICITUD RECIBIENDO LA JUSTIFICACION Y EL HECHO POR PARTE DEL USUARIO
            Solicitud nuevaSolicitud = solicitudesService.crearSolicitud(unaJustificacion, unHecho);

            // REGISTRO LA SOLICITUD UNA VEZ CREADA
            solicitudesService.registrarSolicitud(nuevaSolicitud, this);
        }
    }

    public void aprobarSolicitud(Solicitud unaSolicitud) {
        if (rol.tenesPermiso(Permisos.GESTIONAR_SOLICITUDES)) {
            this.solicitudesService.aprobarSolicitud(unaSolicitud, this);
        }
    }

    public void rechazarSolicitud(Solicitud unaSolicitud) {
        if (rol.tenesPermiso(Permisos.GESTIONAR_SOLICITUDES)) {
            solicitudesService.rechazarSolicitud(unaSolicitud, this);
        }
    }
}