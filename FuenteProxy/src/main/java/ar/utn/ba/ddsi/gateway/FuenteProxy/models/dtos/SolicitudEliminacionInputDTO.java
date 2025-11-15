package ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import lombok.Data;

@Data
public class SolicitudEliminacionInputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
}
