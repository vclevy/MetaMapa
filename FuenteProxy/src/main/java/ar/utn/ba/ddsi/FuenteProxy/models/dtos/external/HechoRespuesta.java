package ar.utn.ba.ddsi.FuenteProxy.models.dtos.external;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HechoRespuesta {
    private List<HechoProxyDTO> data;


}
