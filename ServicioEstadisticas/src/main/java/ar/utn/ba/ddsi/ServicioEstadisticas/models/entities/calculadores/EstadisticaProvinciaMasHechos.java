package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstadisticaProvinciaMasHechos {
        public String calcular(List<Hecho> hechos) {
            if (hechos == null || hechos.isEmpty()) {
                return "No hay hechos disponibles.";
            }

            Map<String, Long> conteoPorProvincia = hechos.stream()
                    .collect(Collectors.groupingBy(Hecho::getProvincia, Collectors.counting()));

            return conteoPorProvincia.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(e -> String.format("Provincia con más hechos: %s (%d reportes)", e.getKey(), e.getValue()))
                    .orElse("No se pudo calcular la estadística.");
        }
}

