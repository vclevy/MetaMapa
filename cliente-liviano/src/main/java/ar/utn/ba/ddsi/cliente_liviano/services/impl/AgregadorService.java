package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.config.MultipartInputResource;
import ar.utn.ba.ddsi.cliente_liviano.jwt.TokenDecoder;
import ar.utn.ba.ddsi.cliente_liviano.models.HechoMapper;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.*;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgregadorService implements IAgregadorService {

    private final WebClient webClient;
    private final DinamicaService dinamicaService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HechoMapper hechoMapper = new HechoMapper();

    @Autowired
    private HttpSession session;

    public AgregadorService(DinamicaService dinamicaService) {
        this.dinamicaService = dinamicaService;
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

    public void editarHecho(HechoDTO hechoDTO, @Nullable MultipartFile[] archivos) {
        try {
            // 🔹 Obtener el hecho existente desde el backend (puede ser cualquier fuente)
            HechoOutputFrontDTO hechoExistente = webClient.get()
                    .uri("/api/hecho/{id}", hechoDTO.getId())
                    .retrieve()
                    .bodyToMono(HechoOutputFrontDTO.class)
                    .block();

            if (hechoExistente == null) {
                throw new RuntimeException("No se encontró el hecho con ID " + hechoDTO.getId());
            }

            String tipoFuente = hechoExistente.getFuenteNombre();
            Long idEnFuente = hechoExistente.getIdEnFuente();

            if ("DINAMICA".equalsIgnoreCase(tipoFuente)) {
                // 🔹 Convertir a FormDTO y sobrescribir campos editables
                HechoFormDTO hechoForm = hechoMapper.toFormDTO(hechoExistente);
                hechoForm.setTitulo(hechoDTO.getTitulo());
                hechoForm.setDescripcion(hechoDTO.getDescripcion());
                hechoForm.setFechaDeAcontecimiento(hechoDTO.getFechaDeAcontecimiento());
                hechoForm.setLatitud(hechoDTO.getLugar().getLatitud());
                hechoForm.setLongitud(hechoDTO.getLugar().getLongitud());
                hechoForm.setNombreDeUsuario(hechoDTO.getNombreDeUsuario());
                hechoForm.setEsAnonimo(false);

                // 🔹 Llamada al servicio Dinámica
                dinamicaService.editarHecho(
                        idEnFuente,
                        hechoForm,
                        archivos != null ? Arrays.asList(archivos) : List.of()
                );

            } else if ("ESTATICA".equalsIgnoreCase(tipoFuente)) {
                // Lógica para fuente ESTATICA (pendiente)
            } else {
                throw new RuntimeException("Tipo de fuente desconocido: " + tipoFuente);
            }

        } catch (Exception e) {
            System.err.println("Error editando hecho desde el Agregador: " + e.getMessage());
            e.printStackTrace();
        }
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




