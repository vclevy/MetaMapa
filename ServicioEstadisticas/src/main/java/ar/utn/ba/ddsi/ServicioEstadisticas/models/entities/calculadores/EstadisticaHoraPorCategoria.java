package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class EstadisticaHoraPorCategoria {

    public ResultadoEstadistica calcular(Coleccion coleccion, String categoria) {
        if (coleccion == null || coleccion.getHechos() == null || coleccion.getHechos().isEmpty()) {
            throw new IllegalArgumentException("No hay datos suficientes para calcular la estadística");
        }

        // Filtrar hechos de la categoría indicada
        List<Hecho> filtrados = coleccion.getHechos().stream()
                .filter(h -> h.getCategoria() != null
                        && h.getCategoria().equalsIgnoreCase(categoria))
                .toList();

        if (filtrados.isEmpty()) {
            throw new IllegalArgumentException("No hay hechos para la categoría: " + categoria);
        }

        // Agrupar por HORA del LocalDateTime
        Map<Integer, Long> conteoPorHora = filtrados.stream()
                .filter(h -> h.getTimestamp() != null)
                .collect(Collectors.groupingBy(
                        h -> h.getTimestamp().getHour(),
                        Collectors.counting()
                ));

        if (conteoPorHora.isEmpty()) {
            throw new IllegalArgumentException("Los hechos no tienen información de fecha/hora válida");
        }

        // Buscar la hora con mayor cantidad de hechos
        Map.Entry<Integer, Long> maxEntry = conteoPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalArgumentException("No se pudo calcular la estadística"));

        return new ResultadoEstadistica(
                "Hora del día con mayor cantidad de hechos en categoría " + categoria,
                maxEntry.getKey().toString(),
                maxEntry.getValue()
        );
    }

}
