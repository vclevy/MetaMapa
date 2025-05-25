package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.HechoRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroCategoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoDesde;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoHasta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IHechosService;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaMapaHechosServices implements IHechosService {

    private final WebClient webClient;

    public MetaMapaHechosServices(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Hecho> obtenerHechos() {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri("/hechos")
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaHecho)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener hechos desde API: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw) {
        List<IFiltroHecho> filtros = new ArrayList<>();

        if (filtrosRaw == null || filtrosRaw.isEmpty()) {
            return obtenerHechos();
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
        return obtenerHechos().stream()
                .filter(hecho -> filtros.stream().allMatch(f -> f.aplica(hecho)))
                .collect(java.util.stream.Collectors.toList());
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
