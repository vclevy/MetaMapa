package domain.hecho.solicitudes;

import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solicitud {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;

    public Solicitud (Hecho unHecho, String unaJustificacion) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
    }
}
