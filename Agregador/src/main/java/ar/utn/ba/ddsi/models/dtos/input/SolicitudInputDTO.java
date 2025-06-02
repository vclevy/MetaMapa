package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudInputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
    private LocalDateTime fechaSolicitud;
    private Usuario visitanteQueCargoLaSolicitud;
}
