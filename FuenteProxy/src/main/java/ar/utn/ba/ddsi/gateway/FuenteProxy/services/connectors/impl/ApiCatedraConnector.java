package ar.utn.ba.ddsi.gateway.FuenteProxy.services.connectors.impl;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.RespuestaAPICatedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;

@Component
public class ApiCatedraConnector{

    private final WebClient webClient;

    @Autowired
    public ApiCatedraConnector(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api-ddsi.disilab.ar/public/api")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer rY3j0CD1b4hpJBNWwZvJkva2NhsGEukeS2pFQkjE2yMBmk6sdlGQ5ATQkpYo")
                .build();
    }

    public List<HechoInputDTO> obtenerHechos() {
        try {
            RespuestaAPICatedra<HechoInputDTO> respuesta = webClient.get()
                    .uri("/desastres")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<RespuestaAPICatedra<HechoInputDTO>>() {})
                    .block();

            return (respuesta != null && respuesta.getData() != null)
                    ? respuesta.getData()
                    : Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Error al obtener hechos desde API: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
