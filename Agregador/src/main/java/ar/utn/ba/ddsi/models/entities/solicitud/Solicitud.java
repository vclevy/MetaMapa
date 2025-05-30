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
    private Long id;
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaDeCargaDeSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();
    private DetectorDeSpam detectorDeSpam;

    public Solicitud (String unaJustificacion, Hecho unHecho, Usuario unUsuario) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = unHecho;
        this.visitanteQueCargoLaSolicitud = unUsuario;
        this.fechaDeCargaDeSolicitud = LocalDateTime.now();
    }

    public void actualizarHistorialDe(Solicitud unaSolicitud, Usuario usuarioModificador) {
        HistorialSolicitud historialSolicitud = new HistorialSolicitud(
                unaSolicitud.getEstado(),
                usuarioModificador
        );
        unaSolicitud.getHistorialSolicitud().add(historialSolicitud);
    }
}