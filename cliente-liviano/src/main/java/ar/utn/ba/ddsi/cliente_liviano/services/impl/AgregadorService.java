package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.CategoriaDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoFiltroDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.TokenProvider;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AgregadorService implements IAgregadorService {

    private final WebClient webClientPublico;
    private final WebClient webClientConToken;
    private final TokenProvider tokenProvider;

    public AgregadorService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.webClientPublico = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
        this.webClientConToken = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader("Authorization", "Bearer " + tokenProvider.getToken())
                .build();
    }


    public List<HechoDTO> obtenerHechos() {
        return webClientPublico.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }


    public List <HechoDTO> obtenerHechosDestacados(){ //TODO mejorar la logica esta
        List<HechoDTO> hechos = webClientPublico.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();

        return hechos.stream().limit(3).collect(Collectors.toList());
    }

    public List<ColeccionDTO> obtenerColecciones(){
        return webClientPublico.get()
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
        return webClientPublico.get()
                .uri("/api/coleccion/{id}", id)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public List<HechoDTO> filtrarHechos(HechoFiltroDTO filtros) {
        return webClientPublico.post()
                .uri("/api/hecho/filtrar")
                .bodyValue(filtros)
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }

    public List<CategoriaDTO> obtenerCategorias() {
        return webClientPublico.get()
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

        return webClientConToken.post()
                .uri("/api/coleccion")
                .bodyValue(body)   // Jackson lo convierte a JSON
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

//    public List<Solicitud> obtenerSolicituds() {
//
//    }

    public boolean eliminarColeccion(Long id) {
        ResponseEntity<String> response = webClientConToken.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/coleccion")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .toEntity(String.class)
                .block();

        return response != null && response.getStatusCode().is2xxSuccessful();
    }

    public boolean saveOrUpdate(ColeccionDTO coleccionOriginal, ColeccionDTO coleccionModificada) {
        boolean exito = true;

        // Comparar título
        if (!Objects.equals(coleccionOriginal.getTitulo(), coleccionModificada.getTitulo())) {
            exito &= actualizarAtributo(coleccionOriginal.getId(), "titulo", coleccionModificada.getTitulo(), null);
        }

        // Comparar descripción
        if (!Objects.equals(coleccionOriginal.getDescripcion(), coleccionModificada.getDescripcion())) {
            exito &= actualizarAtributo(coleccionOriginal.getId(), "descripcion", coleccionModificada.getDescripcion(), null);
        }

        // Comparar algoritmo de consenso
        if (!Objects.equals(coleccionOriginal.getAlgoritmoDeConsenso(), coleccionModificada.getAlgoritmoDeConsenso())) {
            exito &= actualizarAtributo(coleccionOriginal.getId(), "algoritmo", null, coleccionModificada.getAlgoritmoDeConsenso());
        }

        return exito;
    }

    private boolean actualizarAtributo(Long id, String campo, String nuevoValor, String nuevoAlgoritmo) {
        Map<String, Object> body = new HashMap<>();
        body.put("campo", campo);
        if ("algoritmo".equals(campo)) {
            body.put("nuevoAlgoritmoDeConsenso", nuevoAlgoritmo);
        } else {
            body.put("nuevoValor", nuevoValor);
        }

        try {
            webClientConToken.patch()
                    .uri("/{id}/atributo", id)
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return false;
        }
    }
}




