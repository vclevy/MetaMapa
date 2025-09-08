package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EstadisticaCategoriaMasHechos {
    public String calcular(List<Coleccion> colecciones) {

        if (colecciones == null || colecciones.isEmpty()) {
            return "No hay colecciones disponibles.";
        }


        Map<String, Long> conteoPorCategoria = colecciones.stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getHechos().stream())
                .collect(Collectors.groupingBy(
                        Hecho::getCategoria,
                        Collectors.counting()
                ));

        if (conteoPorCategoria.isEmpty()) {
            return "No hay hechos en las colecciones.";
        }

        Map.Entry<String, Long> maxEntry = conteoPorCategoria.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (maxEntry == null) {
            return "No se pudo calcular la estadística.";
        }

        return String.format("Categoría con más hechos: %s (%d reportes)",
                maxEntry.getKey(), maxEntry.getValue());
    }
}