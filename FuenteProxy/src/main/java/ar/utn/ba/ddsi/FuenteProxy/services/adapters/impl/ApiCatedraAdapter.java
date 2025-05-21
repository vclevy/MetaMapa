package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.PaginatedResponseDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.adapters.IApiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component("ApiCatedraAdapter")
public class ApiCatedraAdapter implements IApiAdapter {

    private final WebClient webClient;
    private final HechoAdapter hechoAdapter;

    @Autowired
    public ApiCatedraAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api-ddsi.disilab.ar/public/api")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer rY3j0CD1b4hpJBNWwZvJkva2NhsGEukeS2pFQkjE2yMBmk6sdlGQ5ATQkpYo")
                .build();
        this.hechoAdapter = new HechoAdapter();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        try {
            PaginatedResponseDTO<HechoProxyDTO> respuesta = webClient.get()
                    .uri("/desastres")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<PaginatedResponseDTO<HechoProxyDTO>>() {})
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(hechoAdapter::adaptar)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener hechos desde API: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}