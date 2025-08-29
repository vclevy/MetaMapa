package ar.utn.ba.ddsi.services.mappers;

import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.services.fuentes.Fuente;
import org.springframework.stereotype.Component;

@Component
public class FuenteDeHechosMapper {
    public FuenteDeHechoOutputDTO toDTO(Fuente fuente) {
        FuenteDeHechoOutputDTO dto = new FuenteDeHechoOutputDTO();

        dto.setHandle(fuente.getHandleFuente());
        dto.setTipo(fuente.getTipo());
        dto.setUrlBase(fuente.getUrlBase());
        dto.setUrlProxy(fuente.getUrlProxy());
        dto.setPathProxy(fuente.getPathProxy());
        return dto;
    }
}
