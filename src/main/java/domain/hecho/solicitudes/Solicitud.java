package domain.hecho.solicitudes;

import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Solicitud {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;
    private LocalDateTime fechaSolicitud;

    public Solicitud (Hecho unHecho, String unaJustificacion) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
    }
}
