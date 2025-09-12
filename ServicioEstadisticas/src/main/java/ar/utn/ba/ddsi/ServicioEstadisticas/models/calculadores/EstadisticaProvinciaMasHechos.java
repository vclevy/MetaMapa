package ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EstadisticaProvinciaMasHechos {

    public ResultadoEstadistica calcular(Coleccion coleccion) {
        if (coleccion == null || coleccion.getHechos() == null || coleccion.getHechos().isEmpty()) {
            assert coleccion != null;
            return new ResultadoEstadistica(
                    "Provincia con más hechos",
                    "N/A",
                    0L, coleccion.getTitulo()
            );
        }

        Map<String, Long> conteoPorProvincia = coleccion.getHechos().stream()
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
                        e.getValue(), coleccion.getTitulo()
                ))
                .orElse(new ResultadoEstadistica(
                        "Provincia con más hechos",
                        "N/A",
                        0L,
                        coleccion.getTitulo()
                ));
    }
}

