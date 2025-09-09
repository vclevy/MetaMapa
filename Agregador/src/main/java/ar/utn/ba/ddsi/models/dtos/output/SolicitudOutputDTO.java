package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.HistorialSolicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudOutputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaSolicitud;
    private Long idUsuarioQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();
}
