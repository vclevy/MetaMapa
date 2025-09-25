package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.CategoriaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFiltroDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<ColeccionDTO> obtenerColecciones(){
        return webClient.get()
            .uri("/api/coleccion")
            .retrieve()
            .bodyToFlux(ColeccionDTO.class)
            .collectList()
            .block();
    }

    public HechoDTO obtenerHechoPorId(Long id) {
        return obtenerHechos().stream().filter(h -> Objects.equals(h.getId(), id)).findFirst().orElse(null); //Super ineficiente hay que arreglarlo
    }

    public ColeccionDTO obtenerColeccionPorId(Long id) {
        return webClient.get()
                .uri("/api/coleccion/{id}", id)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
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

    public List<CategoriaDTO> obtenerCategorias() {
        return webClient.get()
                .uri("/api/categorias")
                .retrieve()
                .bodyToFlux(CategoriaDTO.class)
                .collectList()
                .block();
    }

    public ColeccionDTO crearColeccion(String titulo, String descripcion, String algoritmo) {
        // construyo el objeto con los datos que pide el backend
        Map<String, Object> body = new HashMap<>();
        body.put("titulo", titulo);
        body.put("descripcion", descripcion);
        body.put("algoritmo", algoritmo);

        return webClient.post()
                .uri("/api/coleccion")
                .bodyValue(body)   // Jackson lo convierte a JSON
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

//    public List<Solicitud> obtenerSolicituds() {
//
//    }
}
