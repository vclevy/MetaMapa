package ar.utn.ba.ddsi.FuenteProxy.services.connectors.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.Input.HechoInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.ColeccionRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.HechoRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.services.connectors.IMetaMapaConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;

@Component
public class MetaMapaConnectorConcreto implements IMetaMapaConnector {

    private final WebClient webClient;

    public MetaMapaConnectorConcreto(WebClient.Builder builder, @Value("${instanciaMetamapa}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public List<HechoInputDTO> obtenerHechos() {
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

    public List<HechoInputDTO> obtenerHechosDeColeccion(String identificador) {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/colecciones/" + identificador + "/hechos")
                                    .build()
                    )
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            return (respuesta != null && respuesta.getData() != null)
                    ? respuesta.getData()
                    : Collections.emptyList();

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

    public List<HechoInputDTO> obtenerHechosDeColeccionConModo(String identificador, String modoNavegacion) {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/colecciones/" + identificador + "/hechos")
                            .queryParam("modo", modoNavegacion)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            return (respuesta != null && respuesta.getData() != null)
                    ? respuesta.getData()
                    : Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de colección con modo: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
