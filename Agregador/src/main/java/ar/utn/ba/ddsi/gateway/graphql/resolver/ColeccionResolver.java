package ar.utn.ba.ddsi.gateway.graphql.resolver;


import ar.utn.ba.ddsi.gateway.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.gateway.services.coleccionService.ColeccionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ColeccionResolver{

    private final ColeccionService coleccionService;

    public ColeccionResolver(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @QueryMapping
    public ColeccionOutputDTO coleccion(@Argument Long id) {
        return coleccionService.obtenerColeccion(id);
    }

    @QueryMapping
    public List<ColeccionOutputDTO> colecciones() {
        return coleccionService.findAll();
    }
}
