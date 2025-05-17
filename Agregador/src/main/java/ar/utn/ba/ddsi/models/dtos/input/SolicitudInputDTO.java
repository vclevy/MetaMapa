package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;

import java.time.LocalDateTime;

public class SolicitudInputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private LocalDateTime fechaSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
}
