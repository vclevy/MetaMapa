package ar.utn.ba.ddsi.models.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class SolicitudOutputDTO {
    private Integer id;
    private String justificacionDeEliminacion;
    private Integer idHecho; // o el título si preferís
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaDeEvaluacionDeSolicitud;
    private String visitante; // puede ser nombre, email, etc.
    private List<HistorialSolicitudOutputDTO> historialSolicitud;
}
