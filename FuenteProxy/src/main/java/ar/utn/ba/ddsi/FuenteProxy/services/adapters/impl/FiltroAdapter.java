package ar.utn.ba.ddsi.FuenteProxy.services.adapters.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.FiltroHechoDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FiltroAdapter {

    public IFiltroHecho adaptar(FiltroHechoDTO dto) {
        try {
            return switch (dto.getTipo()) {
                case "categoria" -> new FiltroCategoria(dto.getValor());
                case "fecha_acontecimiento_desde" ->
                        new FiltroFechaAcontecimientoDesde(LocalDateTime.parse(dto.getValor()));
                case "fecha_acontecimiento_hasta" ->
                        new FiltroFechaAcontecimientoHasta(LocalDateTime.parse(dto.getValor()));
                case "fecha_desde" -> new FiltroFechaDesde(LocalDateTime.parse(dto.getValor()));
                case "fecha_hasta" -> new FiltroFechaHasta(LocalDateTime.parse(dto.getValor()));
                case "ubicacion" -> {
                    String[] partes = dto.getValor().split(",");
                    double lat = Double.parseDouble(partes[0].trim());
                    double lon = Double.parseDouble(partes[1].trim());
                    yield new FiltroUbicacion(lat, lon);
                }
                default -> throw new IllegalArgumentException("Filtro no soportado: " + dto.getTipo());
            };
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Error al parsear valor del filtro: " + dto.getValor(), e);
        }
    }

    public List<IFiltroHecho> adaptarLista(List<FiltroHechoDTO> dtos) {
        return dtos.stream()
                .map(this::adaptar)
                .collect(Collectors.toList());
    }
}