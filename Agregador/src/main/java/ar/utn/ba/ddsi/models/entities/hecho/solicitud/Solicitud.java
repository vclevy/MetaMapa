package ar.utn.ba.ddsi.models.entities.hecho.solicitud;

import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.spam.DetectorDeSpam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Solicitud {
    private Integer id;
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();
    private DetectorDeSpam detectorDeSpam;

    public Solicitud (Hecho unHecho, String unaJustificacion) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
    }
}