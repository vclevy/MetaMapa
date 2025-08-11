package ar.utn.ba.ddsi.FuenteProxy.conversores;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {

    public Hecho adaptar(HechoProxyDTO dto) {
        Hecho hecho = new Hecho();
        hecho.setId(dto.getId());
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setCategoria(dto.getCategoria() != null ? new Categoria(dto.getCategoria()) : null);
        hecho.setFechaHecho(dto.getFechaHecho());
        hecho.setCreatedAt(dto.getCreatedAt());
        hecho.setUpdatedAt(dto.getUpdatedAt());
        hecho.setLatitud(dto.getLatitud());
        hecho.setLongitud(dto.getLongitud());
        return hecho;
    }

}
