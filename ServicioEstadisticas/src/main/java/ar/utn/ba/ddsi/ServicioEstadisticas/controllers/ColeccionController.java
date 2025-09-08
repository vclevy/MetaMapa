package ar.utn.ba.ddsi.ServicioEstadisticas.controllers;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colecciones")
public class ColeccionController {

    @Autowired
    private IColeccionService coleccionService;

    @GetMapping
    public List<ColeccionOutputDTO> obtenerColecciones() {
        return coleccionService.obtenerColecciones().stream()
                .map(this::toOutputDTO)
                .toList();
    }

    private ColeccionOutputDTO toOutputDTO(Coleccion coleccion) {
        ColeccionOutputDTO dto = new ColeccionOutputDTO();
        dto.setTitulo(coleccion.getTitulo());

        if (coleccion.getHechos() != null) {
            List<HechoOutputDTO> hechos = coleccion.getHechos().stream()
                    .map(this::toOutputDTO)
                    .toList();
            dto.setHechos(hechos);
        }

        return dto;
    }

    private HechoOutputDTO toOutputDTO(Hecho hecho) {
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setCategoria(hecho.getCategoria());
        dto.setProvincia(hecho.getProvincia());
        dto.setTimestamp(hecho.getTimestamp());
        return dto;
    }
}
