package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.jwt.TokenDecoder;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgregadorService implements IAgregadorService {

    private final WebClient webClient;

    @Autowired
    private HttpSession session;

    public AgregadorService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024)
                )
                .build();
    }

    public List<HechoDTO> obtenerHechos() {
        String token = (String) session.getAttribute("token");

        return webClient.get()
                .uri("/api/hecho")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }

    public List <HechoDTO> obtenerHechosDestacados(){
        String token = (String) session.getAttribute("token");

        List<HechoDTO> hechos = webClient.get()
                .uri("/api/hecho/destacados")
                .header("Authorization", "Bearer " + token)
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

    public List<ColeccionDTO> obtenerColeccionesDestacadas(){
        return webClient.get()
                .uri("/api/coleccion/destacadas")
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
        Map<String, Object> body = new HashMap<>();
        body.put("titulo", titulo);
        body.put("descripcion", descripcion);
        body.put("algoritmo", algoritmo);

        String token = (String) session.getAttribute("token");

        return webClient.post()
                .uri("/api/coleccion")
                .header("Authorization", "Bearer " + token)
                .bodyValue(body)   // Jackson lo convierte a JSON
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public boolean eliminarColeccion(Long id) {
        String token = (String) session.getAttribute("token");

        ResponseEntity<String> response = webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/coleccion")
                        .queryParam("id", id)
                        .build())
                .header("Authorization", "Bearer " + token)
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
            webClient.patch()
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

    public ColeccionDTO agregarFuentes(Long idColeccion, FuenteCreateDTO fuenteDTO) {
        String token = (String) session.getAttribute("token");
        return webClient.post()
                .uri("/api/coleccion/{id}/fuentes", idColeccion)
                .header("Authorization", "Bearer " + token) // <-- importante
                .bodyValue(fuenteDTO)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public ColeccionDTO eliminarFuentes(Long idColeccion, Long idFuente) {
        String token = (String) session.getAttribute("token");

        Map<String, Long> body = new HashMap<>();
        body.put("idFuente", idFuente);

        return webClient.method(HttpMethod.DELETE)
                .uri("/api/coleccion/{idColeccion}/fuentes", idColeccion)
                .header("Authorization", "Bearer " + token)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ColeccionDTO.class)
                .block();
    }

    public List<SolicitudDTO> obtenerSolicitudesPendientes() {
        String token = (String) session.getAttribute("token");

        try {
            List<SolicitudDTO> solicitudes = webClient.get()
                    .uri("/api/solicitud/pendientes")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(SolicitudDTO.class)
                    .collectList()
                    .block();
            return (solicitudes != null) ? solicitudes : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void aprobarSolicitud(Long idSolicitud, String usuarioModificador) {
        String token = (String) session.getAttribute("token");

        webClient.patch()
                .uri("/api/solicitud/{id}/aprobar", idSolicitud)
                .header("Authorization", "Bearer " + token)
                .bodyValue(usuarioModificador)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void rechazarSolicitud(Long idSolicitud, String usuarioModificador) {
        String token = (String) session.getAttribute("token");

        webClient.patch()
                .uri("/api/solicitud/{id}/rechazar", idSolicitud)
                .header("Authorization", "Bearer " + token)
                .bodyValue(usuarioModificador)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void solicitarEliminacion(SolicitudDTO solicitudDTO) {
        String respuesta = webClient.post()
                .uri("/api/solicitud")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(solicitudDTO)
                .retrieve()
                .onStatus(HttpStatusCode::isError, r ->
                        r.bodyToMono(String.class)
                                .map(msg -> new RuntimeException("Error al crear la solicitud: " + msg))
                )
                .bodyToMono(String.class)
                .block();
    }

    public void editarHecho(HechoDTO hechoDTO) {
        String token = (String) session.getAttribute("token");

        webClient.put()
                .uri("/api/hecho/{id}", hechoDTO.getId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(hechoDTO)
                .retrieve()
                .onStatus(HttpStatusCode::isError, r ->
                        r.bodyToMono(String.class)
                                .map(msg -> new RuntimeException("Error al editar el hecho: " + msg))
                )
                .toBodilessEntity()
                .block();
    }

}




