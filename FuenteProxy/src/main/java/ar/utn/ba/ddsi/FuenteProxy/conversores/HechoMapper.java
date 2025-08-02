package ar.utn.ba.ddsi.FuenteProxy.conversores;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {

    public Hecho adaptar(HechoProxyDTO dto) {
        return new Hecho(
                dto.getId(),
                dto.getTitulo(),
                dto.getDescripcion(),
                new Categoria(dto.getCategoria()),
                dto.getFechaHecho(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getLatitud(),
                dto.getLongitud()
        );
    }
}
