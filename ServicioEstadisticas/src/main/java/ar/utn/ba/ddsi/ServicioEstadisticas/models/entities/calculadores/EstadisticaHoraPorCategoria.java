package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EstadisticaHoraPorCategoria {

    public String calcular(Coleccion coleccion, String categoria) {
        if (coleccion == null || coleccion.getHechos().isEmpty()) {
            return "No hay hechos en la colección.";
        }

        // Filtrar hechos de la categoría indicada
        List<Hecho> filtrados = coleccion.getHechos().stream()
                .filter(h -> categoria.equalsIgnoreCase(h.getCategoria()))
                .toList();

        if (filtrados.isEmpty()) {
            return "No hay hechos de la categoría: " + categoria;
        }

        // Agrupar por HORA del LocalDateTime
        Map<Integer, Long> conteoPorHora = filtrados.stream()
                .filter(h -> h.getTimestamp() != null)
                .collect(Collectors.groupingBy(
                        h -> h.getTimestamp().getHour(),  // de 0 a 23
                        Collectors.counting()
                ));

        // Encontrar la hora más frecuente
        return conteoPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format(
                        "Hora con más hechos de la categoría '%s': %02d:00 (%d reportes)",
                        categoria, e.getKey(), e.getValue()))
                .orElse("No se pudo calcular la estadística.");
    }
}
