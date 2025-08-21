package ar.utn.ba.ddsi.services.mappers;

import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class ColeccionMapper {
    private final HechoMapper hechoMapper;
    private final FuenteDeHechosMapper fuenteDeHechosMapper;

    public ColeccionMapper(HechoMapper hechoMapper, FuenteDeHechosMapper fuenteDeHechosMapper) {
        this.hechoMapper = hechoMapper;
        this.fuenteDeHechosMapper = fuenteDeHechosMapper;
    }

    public ColeccionOutputDTO toDTO(Coleccion coleccion) {
        ColeccionOutputDTO dto = new ColeccionOutputDTO();
        dto.setTitulo(coleccion.getTitulo());
        dto.setDescripcion(coleccion.getDescripcion());
        dto.setHandle(coleccion.getHandle());
        dto.setAlgoritmoDeConsenso(coleccion.getAlgoritmoDeConsenso() != null ?
                coleccion.getAlgoritmoDeConsenso().getClass().getSimpleName() : "Ninguno");

        List<FuenteDeHechoOutputDTO> fuentesDTO = coleccion.getFuentesDeHechos()
                .stream()
                .map(fuenteDeHechosMapper::toDTO)
                .toList();

        dto.setFuentesDeHechos(fuentesDTO);


        //List<HechoOutputDTO> hechos = coleccion.getHechos().stream()
          //      .map(hechoMapper::toDTO)
          //      .collect(Collectors.toList());
        return dto;
    }
}
