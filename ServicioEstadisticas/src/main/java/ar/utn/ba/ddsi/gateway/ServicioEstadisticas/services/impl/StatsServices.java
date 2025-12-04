package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.calculadores.EstadisticaCategoriaMasHechos;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.calculadores.EstadisticaHoraPorCategoria;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.calculadores.EstadisticaProvinciaMasHechos;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.calculadores.EstadisticaProvinciaPorCategoria;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.repositories.IEstadisticasRepository;
import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.services.IStatsServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Data
public class StatsServices implements IStatsServices {
    @Autowired
    private IEstadisticasRepository estadisticasRepository;
    private final EstadisticaCategoriaMasHechos categoriaMasHechos;
    private final EstadisticaHoraPorCategoria horaPorCategoria;
    private final EstadisticaProvinciaMasHechos provinciaMasHechos;
    private final EstadisticaProvinciaPorCategoria provinciaPorCategoria;

    public List<ResultadoEstadistica> calcularTodas(List<Coleccion> colecciones) throws Exception {
        List<ResultadoEstadistica> resultados = new ArrayList<>();

        Set<String> categoriasUnicas = colecciones.stream()
                .flatMap(c -> c.getHechos().stream())
                .map(Hecho::getCategoria)
                .collect(Collectors.toSet());

        for (String categoria : categoriasUnicas) {
            resultados.add(categoriaMasHechos.calcular(colecciones));

            List<CompletableFuture<ResultadoEstadistica>> futuros = new ArrayList<>();
            for (Coleccion c : colecciones) {
                futuros.add(calcularHoraPorCategoriaAsync(c, categoria));
                futuros.add(calcularProvinciaMasHechosAsync(c));
                futuros.add(calcularProvinciaPorCategoriaAsync(c, categoria));
            }

            CompletableFuture.allOf(futuros.toArray(new CompletableFuture[0])).join();

            for (CompletableFuture<ResultadoEstadistica> f : futuros) {
                resultados.add(f.get());
            }
        }

        List<ResultadoEstadistica> resultadosValidos = resultados.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        estadisticasRepository.saveAll(resultadosValidos);

        return resultados;
    }


    @Async
    public CompletableFuture<ResultadoEstadistica> calcularHoraPorCategoriaAsync(Coleccion c, String categoria) {
        return CompletableFuture.completedFuture(horaPorCategoria.calcular(c, categoria));
    }

    @Async
    public CompletableFuture<ResultadoEstadistica> calcularProvinciaMasHechosAsync(Coleccion c) {
        return CompletableFuture.completedFuture(provinciaMasHechos.calcular(c));
    }

    @Async
    public CompletableFuture<ResultadoEstadistica> calcularProvinciaPorCategoriaAsync(Coleccion c, String categoria) {
        return CompletableFuture.completedFuture(provinciaPorCategoria.calcular(c, categoria));
    }

}