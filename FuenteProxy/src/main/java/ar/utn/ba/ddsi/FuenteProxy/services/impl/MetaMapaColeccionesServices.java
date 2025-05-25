package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.ColeccionRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.HechoRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IColeccionesService;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaMapaColeccionesServices implements IColeccionesService {

    private final WebClient webClient;

    public MetaMapaColeccionesServices(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Coleccion> obtenerColecciones() {
        try {
            ColeccionRespuesta respuesta = webClient.get()
                    .uri("/colecciones")
                    .retrieve()
                    .bodyToMono(ColeccionRespuesta.class)
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaColeccion)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener colecciones: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Hecho> obtenerHechosDeColeccion(String id, Map<String, String> filtros) {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri(uriBuilder -> {
                        UriBuilder builder = uriBuilder.path("/colecciones/" + id + "/hechos");
                        if (filtros != null) filtros.forEach(builder::queryParam);
                        return builder.build();
                    })
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaHecho)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener hechos de colección: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    private Coleccion convertirDTOaColeccion(ColeccionDTO dto) {
        List<Hecho> hechos = dto.getHechosOutputDtos() != null
                ? dto.getHechosOutputDtos().stream().map(this::convertirDTOaHecho).collect(Collectors.toList())
                : Collections.emptyList();

        Coleccion coleccion = new Coleccion(dto.getTitulo(), dto.getDescripcion());
        coleccion.setHechos(hechos);
        coleccion.setCriterioDePertenencia(Collections.emptyList());
        return coleccion;
    }

    private Hecho convertirDTOaHecho(HechoProxyDTO dto) {
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
