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

    private final ISolcitudesService solcitudesService;

    public Usuario(ISolcitudesService solcitudesService) {
        this.solcitudesService = solcitudesService;
    }

    public void solicitarEliminacionDeUnHecho(String unaJustificacion, Hecho unHecho) {
        if (rol.tenesPermiso(Permisos.SOLICITAR_ELIMINACION)) {
            // CREO LA SOLICITUD RECIBIENDO LA JUSTIFICACION Y EL HECHO POR PARTE DEL USUARIO
            Solicitud nuevaSolicitud = solcitudesService.crearSolicitud(unaJustificacion, unHecho);

            // REGISTRO LA SOLICITUD UNA VEZ CREADA
            solcitudesService.registrarSolicitud(nuevaSolicitud, this);
        }
    }

    public void gestionarSolicitudes(List<Solicitud> unasSolicitudes) {
        if (rol.tenesPermiso(Permisos.GESTIONAR_SOLICITUDES)) {
            List<Solicitud> solicitudesPendientes = unasSolicitudes.stream()
                    .filter(s -> s.getEstado() == EstadoDeSolicitudDeEliminacion.PENDIENTE).toList();
            this.solcitudesService.procesarSolicitudes(unasSolicitudes, this);
        }
    }
}