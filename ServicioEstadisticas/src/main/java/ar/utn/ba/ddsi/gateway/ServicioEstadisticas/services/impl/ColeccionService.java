package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.services.IColeccionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class ColeccionService implements IColeccionService {

    private final WebClient webClient;

    public ColeccionService(@Value("${gateway.url}") String urlGateway) {
        this.webClient = WebClient.builder()
                .baseUrl(urlGateway)
                .build();
    }

    public List<Coleccion> obtenerColecciones() {
        return webClient.get()
                .uri("/api/agregador/api/coleccion")
                .retrieve()
                .bodyToFlux(ColeccionInputDTO.class)
                .map(this::inputDTOaColeccion)
                .collectList()
                .block();
    }

    private Coleccion inputDTOaColeccion(ColeccionInputDTO dto) {
        Coleccion coleccion = new Coleccion();
        coleccion.setTitulo(dto.getTitulo());

        if (dto.getHechosDeLaColeccion() != null) {
            List<Hecho> hechos = dto.getHechosDeLaColeccion().stream()
                    .map(this::inputDTOaHecho)
                    .toList();
            coleccion.setHechos(hechos);
        }

        return coleccion;
    }

    private Hecho inputDTOaHecho(HechoInputDTO dto) {
        Hecho hecho = new Hecho();
        hecho.setCategoria(dto.getCategoriaNombre());
        hecho.setProvincia(dto.getLugar() != null ? dto.getLugar().getProvincia() : null);
        hecho.setTimestamp(dto.getFechaDeAcontecimiento());
        return hecho;
    }

    public List<Hecho> obtenerHechosDeColeccion(Long coleccionId, String modoDeNavegacion) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/coleccion/{id}")
                        .queryParam("modoDeNavegacion", modoDeNavegacion)
                        .build(coleccionId))
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .map(this::inputDTOaHecho)
                .collectList()
                .block();
    }
}