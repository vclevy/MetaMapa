package ar.utn.ba.ddsi.conversores;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public class HechoMapper {

    public static Hecho toHecho(HechoInputDTO dto) {
        Hecho hecho = new Hecho(
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getFechaDelHecho(),
                dto.getLugar()
        );

        Categoria categoria = new Categoria(dto.getCategoria());
        hecho.setCategoria(categoria);
        return hecho;
    }

    public static HechoOutputDTO toOutputDTO(Hecho hecho) {
        return new HechoOutputDTO(
                hecho.getId(),
                hecho.getTitulo(),
                hecho.getDescripcion(),
                hecho.getCategoria(),
                hecho.getFechaDeAcontecimiento(),
                hecho.getFechaDeCarga(),
                hecho.getLugar(),
                hecho.getMultimedia()
        );
    }
}
