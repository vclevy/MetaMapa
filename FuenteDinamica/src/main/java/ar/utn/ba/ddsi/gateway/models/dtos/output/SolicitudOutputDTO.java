package ar.utn.ba.ddsi.gateway.models.dtos.output;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.solicitudes.HistorialSolicitud;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class SolicitudOutputDTO {
    private Integer id;
    private String justificacionDeEliminacion;
    private Integer idHecho;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private String visitante;
    private List<HistorialSolicitud> historialSolicitud;
}
