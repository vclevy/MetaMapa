package ar.utn.ba.ddsi.services.mappers;

import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColeccionMapper {
    private final HechoMapper hechoMapper;

    public ColeccionMapper(HechoMapper hechoMapper) {
        this.hechoMapper = hechoMapper;
    }

    public ColeccionOutputDTO toDTO(Coleccion coleccion) {
        ColeccionOutputDTO dto = new ColeccionOutputDTO();
        dto.setTitulo(coleccion.getTitulo());
        dto.setDescripcion(coleccion.getDescripcion());

        List<HechoOutputDTO> hechos = coleccion.getHechos().stream()
                .map(hechoMapper::toDTO)
                .collect(Collectors.toList());

        dto.setHechosDeLaColeccion(hechos);
        return dto;
    }
}
