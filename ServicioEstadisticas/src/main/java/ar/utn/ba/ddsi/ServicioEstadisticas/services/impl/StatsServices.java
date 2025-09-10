package ar.utn.ba.ddsi.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores.EstadisticaCategoriaMasHechos;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores.EstadisticaHoraPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores.EstadisticaProvinciaMasHechos;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.calculadores.EstadisticaProvinciaPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.ResultadoEstadistica;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.repositories.IEstadisticasRepository;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IStatsServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Data
public class StatsServices implements IStatsServices {
    @Autowired
    private IEstadisticasRepository estadisticasRepository;
    private final EstadisticaCategoriaMasHechos categoriaMasHechos;
    private final EstadisticaHoraPorCategoria horaPorCategoria;
    private final EstadisticaProvinciaMasHechos provinciaMasHechos;
    private final EstadisticaProvinciaPorCategoria provinciaPorCategoria;

    public List<ResultadoEstadistica> calcularTodas(List<Coleccion> colecciones, String categoria) throws Exception {
        List<ResultadoEstadistica> resultados = new ArrayList<>();
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

        // falta lo de spam
        estadisticasRepository.saveAll(resultados);
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