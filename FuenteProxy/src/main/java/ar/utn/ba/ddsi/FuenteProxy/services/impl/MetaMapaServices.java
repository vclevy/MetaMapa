package ar.utn.ba.ddsi.FuenteProxy.services.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.ColeccionRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.external.HechoRespuesta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Categoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroCategoria;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoDesde;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.FiltroFechaAcontecimientoHasta;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.services.IMetamapaServices;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetaMapaServices implements IMetamapaServices {

    private final WebClient webClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public MetaMapaServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("${instanciaMetamapa}")
                .build();
    }

    @Override
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
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener hechos desde API: " + e.getMessage());
            return Collections.emptyList();
        }
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

    @Override
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

    @Override
    public List<Coleccion> obtenerColecciones() {
        try {
            ColeccionRespuesta respuesta = webClient.get()
                    .uri("/colecciones")
                    .retrieve()
                    .bodyToMono(ColeccionRespuesta.class)
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaColeccion)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener colecciones: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Coleccion convertirDTOaColeccion(ColeccionDTO dto) {
        Coleccion coleccion = new Coleccion(dto.getTitulo(), dto.getDescripcion());

        if (dto.getHechosOutputDtos() != null) {
            List<Hecho> hechos = dto.getHechosOutputDtos().stream()
                    .map(this::convertirDTOaHecho)
                    .collect(Collectors.toList());
            coleccion.setHechos(hechos);
        } else {
            coleccion.setHechos(Collections.emptyList());
        }

        coleccion.setCriterioDePertenencia(Collections.emptyList());

        return coleccion;
    }

    @Override
    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        try {
            HechoRespuesta respuesta = webClient.get()
                    .uri(uriBuilder -> {
                        UriBuilder builder = uriBuilder
                                .path("/colecciones/" + identificador + "/hechos");
                        if (filtros != null) {
                            filtros.forEach(builder::queryParam);
                        }
                        return builder.build();
                    })
                    .retrieve()
                    .bodyToMono(HechoRespuesta.class)
                    .block();

            if (respuesta != null && respuesta.getData() != null) {
                return respuesta.getData().stream()
                        .map(this::convertirDTOaHecho)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }

        } catch (Exception e) {
            System.err.println("Error al obtener hechos de colección: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion) {
        try {
            SolicitudEliminacionInputDTO dto = new SolicitudEliminacionInputDTO();
            dto.setHecho(unHecho);
            dto.setJustificacionDeEliminacion(justificacion);

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

