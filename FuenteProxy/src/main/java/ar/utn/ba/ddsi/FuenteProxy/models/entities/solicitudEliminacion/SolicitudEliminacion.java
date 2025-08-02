package ar.utn.ba.ddsi.FuenteProxy.models.entities.solicitudEliminacion;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class SolicitudEliminacion {
    private UUID id;
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoSolicitudEliminacion estado;
    private LocalDateTime fechaSolicitud;

    public SolicitudEliminacion (Hecho unHecho, String unaJustificacion) {
        this.id = UUID.randomUUID();
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
        this.estado = EstadoSolicitudEliminacion.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
    }
}
