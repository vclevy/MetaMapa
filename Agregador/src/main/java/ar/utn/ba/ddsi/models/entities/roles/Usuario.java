package ar.utn.ba.ddsi.models.entities.roles;

import ar.utn.ba.ddsi.models.entities.hecho.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.Permisos;
import ar.utn.ba.ddsi.services.ISolcitudesService;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Usuario {
    private String nombre;
    private IRol rol;

    private final ISolcitudesService solcitudesService;

    public Usuario(ISolcitudesService solcitudesService) {
        this.solcitudesService = solcitudesService;
    }

    public void solicitarEliminacionDeUnHecho(Solicitud unaSolicitud) {
        if (rol.tenesPermiso(Permisos.SOLICITAR_ELIMINACION)) {
            solcitudesService.registrarSolicitud(unaSolicitud, this);
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