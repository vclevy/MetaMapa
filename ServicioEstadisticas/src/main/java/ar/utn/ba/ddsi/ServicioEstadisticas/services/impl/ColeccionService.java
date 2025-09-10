package ar.utn.ba.ddsi.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IColeccionService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class ColeccionService implements IColeccionService {

    private final WebClient webClient;

    public ColeccionService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public List<Coleccion> obtenerColecciones() {
        return webClient.get()
                .uri("/api/coleccion")
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
        hecho.setTimestamp(dto.getFechaDeAcontecimiento() != null ?
                dto.getFechaDeAcontecimiento(): null);
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