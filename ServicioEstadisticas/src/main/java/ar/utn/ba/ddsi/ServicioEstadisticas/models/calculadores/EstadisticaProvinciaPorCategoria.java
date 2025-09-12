package ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Component
public class EstadisticaProvinciaPorCategoria {

    public ResultadoEstadistica calcular(Coleccion coleccion, String categoria) {
        if (coleccion == null || coleccion.getHechos() == null) {
            return new ResultadoEstadistica(
                    "Provincia con más hechos de la categoría " + categoria,
                    "N/A",
                    0L,
                    coleccion != null ? coleccion.getTitulo() : "N/A"
            );
        }

        List<Hecho> filtrados = coleccion.getHechos().stream()
                .filter(h -> h.getCategoria() != null &&
                        h.getCategoria().trim().equalsIgnoreCase(categoria.trim()))
                .toList();

        if (filtrados.isEmpty()) {
            return new ResultadoEstadistica(
                    "Provincia con más hechos de la categoría " + categoria,
                    "N/A",
                    0L,
                    coleccion.getTitulo()
            );
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
                        e.getValue(),
                        coleccion.getTitulo()
                ))
                .orElse(new ResultadoEstadistica(
                        "Provincia con más hechos de la categoría " + categoria,
                        "N/A",
                        0L,
                        coleccion.getTitulo()
                ));
    }
}
