package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudOutputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();
}
