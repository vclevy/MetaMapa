package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFiltroDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AgregadorService implements IAgregadorService {

    private final WebClient webClient;

    public AgregadorService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public List<HechoDTO> obtenerHechos() {
        return webClient.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }

    public List <HechoDTO> obtenerHechosDestacados(){ //TODO mejorar la logica esta
        List<HechoDTO> hechos = webClient.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();

        return hechos.stream().limit(3).collect(Collectors.toList());
    }

    public List<ColeccionInputDTO> obtenerColecciones(){
        return webClient.get()
            .uri("/api/coleccion")
            .retrieve()
            .bodyToFlux(ColeccionInputDTO.class)
            .collectList()
            .block();
    }

    public HechoDTO obtenerHechoPorId(Long id) {
        return obtenerHechos().stream().filter(h -> Objects.equals(h.getId(), id)).findFirst().orElse(null); //Super ineficiente hay que arreglarlo
    }

    public ColeccionInputDTO obtenerColeccionPorId(Long id) {
        return webClient.get()
                .uri("/api/coleccion/{id}", id)
                .retrieve()
                .bodyToMono(ColeccionInputDTO.class)
                .block();
    }

    public List<HechoDTO> filtrarHechos(HechoFiltroDTO filtros) {
        return webClient.post()
                .uri("/api/hecho/filtrar")
                .bodyValue(filtros)
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }

}
