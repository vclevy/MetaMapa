package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Component
public class EstadisticaProvinciaPorCategoria {

    public ResultadoEstadistica calcular(Coleccion coleccion, String categoria) {
        if (coleccion == null || coleccion.getHechos() == null) {
            throw new IllegalArgumentException("No se puede calcular estadística por categoría");
        }

        List<Hecho> filtrados = coleccion.getHechos().stream()
                .filter(h -> h.getCategoria() != null &&
                        h.getCategoria().trim().equalsIgnoreCase(categoria.trim()))
                .toList();

        if (filtrados.isEmpty()) {
            throw new IllegalArgumentException("No hay hechos para la categoría: " + categoria);
        }

        Map<String, Long> conteoPorProvincia = filtrados.stream()
                .filter(h -> h.getProvincia() != null)
                .collect(Collectors.groupingBy(
                        Hecho::getProvincia,
                        Collectors.counting()
                ));

        return conteoPorProvincia.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> new ResultadoEstadistica(
                        "Provincia con más hechos de la categoría " + categoria,
                        e.getKey(),
                        e.getValue(), null
                ))
                .orElse(new ResultadoEstadistica(
                        "Provincia con más hechos de la categoría " + categoria,
                        "N/A",
                        0L, null
                ));
    }
}


