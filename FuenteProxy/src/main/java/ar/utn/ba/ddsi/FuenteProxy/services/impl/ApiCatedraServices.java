package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.PaginatedResponseDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.*;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.FuenteProxy.services.IApiCatedraServices;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiCatedraServices implements IApiCatedraServices {

    private final WebClient webClient;
    private final IHechosRepository proxyRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");

    public ApiCatedraServices(WebClient.Builder webClientBuilder, IHechosRepository proxyRepository) {
        String token = "rY3j0CD1b4hpJBNWwZvJkva2NhsGEukeS2pFQkjE2yMBmk6sdlGQ5ATQkpYo";
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
    public List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw) {
        List<IFiltroHecho> filtros = new ArrayList<>();

        if (filtrosRaw == null || filtrosRaw.isEmpty()) {
            return obtenerHechosDesdeAPI();
        }

        if (filtrosRaw.containsKey("categoria")) {
            filtros.add(new FiltroCategoria(filtrosRaw.get("categoria")));
        }

        String desdeStr = filtrosRaw.get("fecha_acontecimiento_desde");
        if (desdeStr != null && !desdeStr.isBlank()) {
            try {
                LocalDateTime desde = LocalDateTime.parse(desdeStr);
                filtros.add(new FiltroFechaAcontecimientoDesde(desde));
            } catch (DateTimeParseException e) {
                System.err.println("Formato inválido para fecha_acontecimiento_desde: " + desdeStr);
            }
        }

        String hasta = filtrosRaw.get("fecha_acontecimiento_hasta");
        if (hasta != null && !hasta.isBlank()) {
            try {
                LocalDateTime fechaHasta = LocalDateTime.parse(hasta);
                filtros.add(new FiltroFechaAcontecimientoHasta(fechaHasta));
            } catch (DateTimeParseException e) {
                System.err.println("Error al parsear fecha_acontecimiento_hasta: " + e.getMessage());
            }
        }
        return obtenerHechosDesdeAPI().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> obtenerTodasLasColecciones() {
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
                new Categoria(dto.getCategoria()),
                dto.getFechaHecho(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getLatitud(),
                dto.getLongitud()
        );
    }

}
