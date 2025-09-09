package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Component
public class EstadisticaProvinciaPorCategoria {

    public ResultadoEstadistica calcular(List<Coleccion> colecciones, String categoria) {
        if (colecciones == null || colecciones.isEmpty() ||
                colecciones.stream().anyMatch(c -> c.getHechos() == null)) {
            return new ResultadoEstadistica(
                    "Provincia con más hechos de categoría " + categoria,
                    "N/A",
                    null
            );
        }

        // Unir todos los hechos de todas las colecciones
        List<Hecho> filtrados = colecciones.stream()
                .flatMap(c -> c.getHechos().stream())
                .filter(h -> h.getCategoria() != null
                        && categoria.equalsIgnoreCase(h.getCategoria()))
                .toList();

        if (filtrados.isEmpty()) {
            return new ResultadoEstadistica(
                    "Provincia con más hechos de categoría " + categoria,
                    "N/A",
                    null
            );
        }

        // Agrupar por provincia y contar
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
                        e.getValue()
                ))
                .orElse(new ResultadoEstadistica(
                        "Provincia con más hechos de la categoría " + categoria,
                        "N/A",
                        null
                ));
    }


}

