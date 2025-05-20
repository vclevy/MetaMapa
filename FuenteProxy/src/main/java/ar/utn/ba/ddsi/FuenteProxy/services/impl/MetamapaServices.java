package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IMetamapaServices;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MetamapaServices implements IMetamapaServices {

    private final WebClient webClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public MetamapaServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://metamapa.instancia/api")
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos(Map<String, String> filtros) {
        try {
            WebClient.RequestHeadersUriSpec<?> request = webClient.get().uri(uriBuilder -> {
                UriBuilder builder = uriBuilder.path("/hechos");
                if (filtros != null) {
                    filtros.forEach(builder::queryParam);
                }
                return builder.build();
            });

            return request.retrieve()
                    .bodyToFlux(Hecho.class)
                    .collectList()
                    .block();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de MetaMapa: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> obtenerColecciones() {
        try {
            return webClient.get()
                    .uri("/colecciones")
                    .retrieve()
                    .bodyToFlux(String.class) // suponiendo que devuelve una lista de strings
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.err.println("Error al obtener colecciones: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        try {
            WebClient.RequestHeadersUriSpec<?> request = webClient.get().uri(uriBuilder -> {
                UriBuilder builder = uriBuilder.path("/colecciones/" + identificador + "/hechos");
                if (filtros != null) {
                    filtros.forEach(builder::queryParam);
                }
                return builder.build();
            });

            return request.retrieve()
                    .bodyToFlux(Hecho.class)
                    .collectList()
                    .block();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de colección: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean enviarSolicitudEliminacion(SolicitudEliminacionDTO solicitud) {
        try {
            webClient.post()
                    .uri("/solicitudes")
                    .bodyValue(solicitud)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (Exception e) {
            System.err.println("Error al enviar solicitud de eliminación: " + e.getMessage());
            return false;
        }
    }
}

