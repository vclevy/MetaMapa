package ar.utn.ba.ddsi.FuenteProxy.models.dtos;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import lombok.Data;

@Data
public class SolicitudEliminacionInputDTO {
    private String justificacionDeEliminacion;
    private Hecho hecho;
}
