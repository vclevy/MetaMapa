package ar.utn.ba.ddsi.models.entities.solicitud;

import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.spam.DetectorDeSpam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Solicitud {
    private Integer id;
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaDeCargaDeSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();
    private DetectorDeSpam detectorDeSpam;

    public Solicitud (String unaJustificacion, Hecho unHecho, Usuario unUsuario) {
        // VARIABLES QUE ME LLEGAN POR PARAMETRO
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
        this.visitanteQueCargoLaSolicitud = unUsuario;

        // VARIABLES QUE INCILIZO UNA VEZ QUE SE CREA LA SOLICITUD
        this.id = UUID.randomUUID().hashCode();
        this.fechaDeCargaDeSolicitud = LocalDateTime.now();
    }
}