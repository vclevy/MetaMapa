package ar.utn.ba.ddsi.FuenteProxy.models.dtos.external;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.Input.HechoInputDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HechoRespuesta {
    private List<HechoInputDTO> data;
}
