package ar.utn.ba.ddsi.FuenteProxy.conversores;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Lugar;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {

    public Hecho adaptar(HechoInputDTO dto) {
        Hecho hecho = new Hecho();
        hecho.setId(dto.getId());
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setCategoria(dto.getCategoria() != null ? new Categoria(dto.getCategoria()) : null);
        hecho.setFechaDeAcontecimiento(dto.getFechaHecho());
        hecho.setCreatedAt(dto.getCreatedAt());
        hecho.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getLatitud() != null || dto.getLongitud() != null) {
            Lugar lugar = new Lugar();
            lugar.setLatitud(dto.getLatitud());
            lugar.setLongitud(dto.getLongitud());
            hecho.setLugar(lugar);
        }

        return hecho;
    }

    public Hecho adaptar(HechoOutputDTO dto) {
        Hecho hecho = new Hecho();
        hecho.setId(dto.getId());
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setCategoria(dto.getCategoria() != null ? new Categoria(dto.getCategoria()) : null);
        hecho.setFechaDeAcontecimiento(dto.getFechaHecho());
        hecho.setCreatedAt(dto.getCreatedAt());
        hecho.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getLatitud() != null || dto.getLongitud() != null) {
            Lugar lugar = new Lugar();
            lugar.setLatitud(dto.getLatitud());
            lugar.setLongitud(dto.getLongitud());
            hecho.setLugar(lugar);
        }

        return hecho;
    }

    public HechoOutputDTO aOutputDTO(Hecho hecho) {
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setId(hecho.getId());
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoria(hecho.getCategoria() != null ? hecho.getCategoria().getNombre() : null);
        dto.setFechaHecho(hecho.getFechaDeAcontecimiento());
        dto.setCreatedAt(hecho.getCreatedAt());
        dto.setUpdatedAt(hecho.getUpdatedAt());

        if (hecho.getLugar() != null) {
            dto.setLatitud(hecho.getLugar().getLatitud());
            dto.setLongitud(hecho.getLugar().getLongitud());
        }

        return dto;
    }
}
