package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.PaginatedResponseDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.IProxyRepository;
import ar.utn.ba.ddsi.FuenteProxy.services.IProxyServices;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProxyServices implements IProxyServices {

    private final WebClient webClient;
    private final IProxyRepository proxyRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");

    public ProxyServices(WebClient.Builder webClientBuilder, IProxyRepository proxyRepository) {
        String token = "rY3j0CD1b4hpJBNWwZvJkva2NhsGEukeS2pFQkjE2yMBmk6sdlGQ5ATQkpYo"; // poné tu token real acá
        this.webClient = webClientBuilder
                .baseUrl("https://api-ddsi.disilab.ar/public/api")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        this.proxyRepository = proxyRepository;
    }

    @Override
    public List<Hecho> obtenerHechosDesdeAPI() {
        try {
            PaginatedResponseDTO<HechoProxyDTO> respuesta = webClient.get()
                    .uri("/desastres")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<PaginatedResponseDTO<HechoProxyDTO>>() {})
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaHecho)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener hechos desde API: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Hecho> obtenerTodosLosHechos(Map<String, String> filtros) {
        return List.of();
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        return List.of();
    }

    private Hecho convertirDTOaHecho(HechoProxyDTO dto) {
        return new Hecho(
                dto.getId(),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getCategoria(),
                dto.getFechaHecho(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getLatitud(),
                dto.getLongitud()
        );
    }

}
