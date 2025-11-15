package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.calculadores;

import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class EstadisticaHoraPorCategoria {

    public ResultadoEstadistica calcular(Coleccion coleccion, String categoria) {
        if (coleccion == null) {
            throw new IllegalArgumentException("No hay datos suficientes para calcular la estadística");
        }

        List<Hecho> filtrados = coleccion.getHechos().stream()
                .filter(h -> h.getCategoria() != null &&
                        h.getCategoria().trim().equalsIgnoreCase(categoria.trim()) &&
                        h.getTimestamp() != null)
                .toList();

        if (filtrados.isEmpty()) {
            return null;
        }

        Map<Integer, Long> conteoPorHora = filtrados.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getTimestamp().getHour(),
                        Collectors.counting()
                ));

        if (conteoPorHora.isEmpty()) {
            return null;
        }

        Map.Entry<Integer, Long> maxEntry = conteoPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (maxEntry == null) {
            System.out.println("No se pudo calcular la estadística");
            return null;
        }

        Long valorFinal = maxEntry.getValue() != null ? maxEntry.getValue() : 0L;

        String claveFinal = maxEntry.getKey() != null
                ? String.format("%02d:00 hs", maxEntry.getKey())
                : "00:00 hs";

        return new ResultadoEstadistica(
                "Hora del día con mayor cantidad de hechos en categoría " + categoria,
                claveFinal,
                valorFinal,
                coleccion.getTitulo()
        );
    }
}
