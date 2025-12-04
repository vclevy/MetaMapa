package ar.utn.ba.ddsi.gateway.models.dtos.input.solicitud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudInputDTO {
    private String justificacion;
    private Long idHecho;
    private String nombreDeUsuario;
}
