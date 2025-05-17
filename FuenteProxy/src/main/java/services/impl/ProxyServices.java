package services.impl;

import models.dtos.HechoProxyDTO;
import models.entities.Hecho;
import models.repositories.IProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import services.IProxyServices;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ProxyServices implements IProxyServices {

    private final WebClient webClient;

    @Autowired
    private IProxyRepository proxyRepository;

    @Autowired
    public ProxyServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-ddsi.disilab.ar/public/api").build();
    }

    @Override
    public List<Hecho> obtenerHechosDesdeAPI() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<HechoProxyDTO>>() {})
                .map(hechosDTOs -> hechosDTOs.stream()
                        .map(this::convertirDTOaHecho)
                        .collect(Collectors.toList()))
                .block();
    }

    @Override
    public List<Hecho> obtenerTodosLosHechos(Map<String, String> filtros) {
        return proxyRepository.obtenerHechos(filtros);
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        return proxyRepository.obtenerHechosDeColeccion(identificador, filtros);
    }

    private Hecho convertirDTOaHecho(HechoProxyDTO dto) {
        return new Hecho(
                dto.getId(),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getCategoria(),
                dto.getFechaAcontecimiento(),
                dto.getFechaCarga() != null ? dto.getFechaCarga() : LocalDateTime.now(),
                dto.getLatitud(),
                dto.getLongitud(),
                dto.getFuente()
        );
    }
}
