package ar.utn.ba.ddsi.FuenteProxy.services.connectors;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.ColeccionRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.HechoRespuesta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class MetaMapaConnector {

    private final WebClient webClient;

    public MetaMapaConnector(WebClient.Builder builder, @Value("${instanciaMetamapa}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public List<HechoProxyDTO> obtenerHechos() {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri("/hechos")
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            return (respuesta != null && respuesta.getData() != null) ?
                    respuesta.getData() :
                    Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<HechoProxyDTO> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri(uriBuilder -> {
                        UriBuilder builder = uriBuilder.path("/colecciones/" + identificador + "/hechos");
                        if (filtros != null) {
                            filtros.forEach(builder::queryParam);
                        }
                        return builder.build();
                    })
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            return (respuesta != null && respuesta.getData() != null) ?
                    respuesta.getData() :
                    Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de colección: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<ColeccionDTO> obtenerColecciones() {
        try {
            ColeccionRespuesta respuesta = webClient.get()
                    .uri("/colecciones")
                    .retrieve()
                    .bodyToMono(ColeccionRespuesta.class)
                    .block();

            return (respuesta != null && respuesta.getData() != null) ?
                    respuesta.getData() :
                    Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Error al obtener colecciones: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean enviarSolicitudEliminacion(SolicitudEliminacionInputDTO dto) {
        try {
            webClient.post()
                    .uri("/solicitudes")
                    .bodyValue(dto)
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
