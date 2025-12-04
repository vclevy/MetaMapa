package ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.external;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.ColeccionDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ColeccionRespuesta {
    private List<ColeccionDTO> data;
}
