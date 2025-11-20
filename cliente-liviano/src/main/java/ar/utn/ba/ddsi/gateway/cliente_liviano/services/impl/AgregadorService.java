package ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.IAgregadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public AgregadorService(@Value("${gateway.url}") String baseGatewayUrl) {
        String fullUrl = baseGatewayUrl + "/api/agregador";

        this.webClient = WebClient.builder()
                .baseUrl(fullUrl)
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

    public List<HechoDTO> obtenerHechosVisibles() {
        String token = (String) session.getAttribute("token");

        return webClient.get()
                .uri("/api/hecho/visibles")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }
    public List<HechoDTO> obtenerHechosPendientes() {
        String token = (String) session.getAttribute("token");

        return webClient.get()
                .uri("/api/hecho/pendientes")
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
        String token = (String) session.getAttribute("token");

        // Si no cambió nada, salimos
        if (coleccionOriginal != null
                && Objects.equals(coleccionOriginal.getTitulo(), coleccionModificada.getTitulo())
                && Objects.equals(coleccionOriginal.getDescripcion(), coleccionModificada.getDescripcion())
                && Objects.equals(coleccionOriginal.getAlgoritmoDeConsenso(), coleccionModificada.getAlgoritmoDeConsenso())) {

            System.out.println(">>> No hubo cambios. No hacemos PUT.");
            return true;
        }

        System.out.println(">>> Hay cambios. Preparando PUT...");


        try {
            String url = "/api/coleccion/" + coleccionModificada.getId() + "/editar";

            System.out.println(">>> Llamando PUT a: " + url);
            System.out.println(">>> Body enviado:");
            System.out.println("    Título: " + coleccionModificada.getTitulo());
            System.out.println("    Descripción: " + coleccionModificada.getDescripcion());
            System.out.println("    Algoritmo: " + coleccionModificada.getAlgoritmoDeConsenso());

            webClient.put()
                    .uri(url)
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(coleccionModificada)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            System.out.println(">>> PUT ejecutado correctamente. Llegó al agregadorservice");
            return true;

        } catch (Exception e) {
            System.out.println(">>> ERROR en saveOrUpdate()");
            e.printStackTrace();   // <<<<<< ESTE ES EL LOG IMPORTANTE
            return false;
        }
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

    public void eliminarHecho(Long idHecho) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            throw new IllegalStateException("No hay token en sesión.");
        }

        webClient
                .put()
                .uri("/api/hecho/eliminar/{id}", idHecho)   // PATH VAR
                .headers(h -> h.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(idHecho)                          // BODY con el mismo id (la API lo pide)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class)
                                .map(msg -> new RuntimeException("No se pudo eliminar el hecho: " + msg))
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class)
                                .map(msg -> new RuntimeException("Error del servidor al eliminar el hecho: " + msg))
                )
                .toBodilessEntity()
                .block();
    }

    public List<HechoDTO> obtenerHechosDeColeccion(Long id, String modo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/coleccion/{id}/hechos")
                        .queryParam("modoDeNavegacion", modo)
                        .build(id))
                .retrieve()
                .bodyToFlux(HechoDTO.class)
                .collectList()
                .block();
    }

    public void aprobarHecho(Long idHecho) {
        String token = (String) session.getAttribute("token");

        webClient.patch()
                .uri("/api/hecho/{id}/aprobar", idHecho)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}




