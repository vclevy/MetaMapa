package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;

import java.util.*;
import java.util.stream.Collectors;

public class EstadisticaProvinciaPorCategoria {

    public String calcular(List<Coleccion> colecciones, String categoria) {
        if (colecciones == null || colecciones.isEmpty() ||
                colecciones.stream().anyMatch(c -> c.getHechos() == null)) {
            return "No hay hechos en las colecciones.";
        }

        // Unir todos los hechos de todas las colecciones
        List<Hecho> filtrados = colecciones.stream()
                .flatMap(c -> c.getHechos().stream())
                .filter(h -> categoria.equalsIgnoreCase(h.getCategoria()))
                .toList();

        if (filtrados.isEmpty()) {
            return "No hay hechos de la categoría: " + categoria;
        }

        // Agrupar por provincia y contar
        Map<String, Long> conteoPorProvincia = filtrados.stream()
                .collect(Collectors.groupingBy(Hecho::getProvincia, Collectors.counting()));

        // Encontrar la provincia con más hechos
        return conteoPorProvincia.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format(
                        "Provincia con más hechos de la categoría '%s': %s (%d reportes)",
                        categoria, e.getKey(), e.getValue()))
                .orElse("No se pudo calcular la estadística.");
    }

}

