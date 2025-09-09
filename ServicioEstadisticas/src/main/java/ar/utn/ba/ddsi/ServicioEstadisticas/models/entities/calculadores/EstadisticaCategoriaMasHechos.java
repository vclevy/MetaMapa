package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EstadisticaCategoriaMasHechos {
    public ResultadoEstadistica calcular(List<Coleccion> colecciones) {

        if (colecciones == null || colecciones.isEmpty()) {
            throw new IllegalArgumentException("(Estadistica mas hechos1) No hay datos suficientes para calcular la estadística");
        }

        Map<String, Long> conteoPorCategoria = colecciones.stream()
                .filter(Objects::nonNull)
                .flatMap(c -> c.getHechos().stream())
                .collect(Collectors.groupingBy(
                        Hecho::getCategoria,
                        Collectors.counting()
                ));

        if (conteoPorCategoria.isEmpty()) {
            throw new IllegalArgumentException(" (Estadistica mas hechos2) No hay datos suficientes para calcular la estadística");
        }

        Map.Entry<String, Long> maxEntry = conteoPorCategoria.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (maxEntry == null) {
            throw new IllegalArgumentException("(Estadistica mas hechos3) No hay datos suficientes para calcular la estadística");
        }

        return new ResultadoEstadistica("Categoria con mas hechos",maxEntry.getKey(),maxEntry.getValue(), null);
    }
}