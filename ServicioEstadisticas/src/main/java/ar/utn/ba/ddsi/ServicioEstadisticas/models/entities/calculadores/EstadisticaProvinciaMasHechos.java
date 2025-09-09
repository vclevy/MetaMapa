package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;
@Component
public class EstadisticaProvinciaMasHechos {
    public ResultadoEstadistica calcular(List<Hecho> hechos) {
        if (hechos == null || hechos.isEmpty()) {
            return new ResultadoEstadistica(
                    "Provincia con más hechos",
                    "N/A",
                    null
            );
        }

        Map<String, Long> conteoPorProvincia = hechos.stream()
                .filter(h -> h.getProvincia() != null)
                .collect(Collectors.groupingBy(
                        Hecho::getProvincia,
                        Collectors.counting()
                ));

        return conteoPorProvincia.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> new ResultadoEstadistica(
                        "Provincia con más hechos",
                        e.getKey(),
                        e.getValue()
                ))
                .orElse(new ResultadoEstadistica(
                        "Provincia con más hechos",
                        "N/A",
                        null
                ));
    }

}

