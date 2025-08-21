package ar.utn.ba.ddsi.models.dtos.input.solicitud;

import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.AlgoritmoDeConsenso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudInputDTO {
    private String justificacion;
    private Long idHecho;
}
