package ar.utn.ba.ddsi.ServicioEstadisticas.services.impl;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Hecho;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaCategoriaMasHechos;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaHoraPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaProvinciaMasHechos;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.calculadores.EstadisticaProvinciaPorCategoria;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador.Exportador;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.exportador.ExportadorCSV;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.resultadosEstadisticas.ResultadoEstadistica;
import ar.utn.ba.ddsi.ServicioEstadisticas.models.repositories.IEstadisticasRepository;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IColeccionService;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.IStatsServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<ResultadoEstadistica> calcularTodas(List<Coleccion> colecciones, String categoria) throws Exception {
        List<ResultadoEstadistica> resultados = new ArrayList<>();
        resultados.add(categoriaMasHechos.calcular(colecciones));

        for (Coleccion c : colecciones) {
            resultados.add(horaPorCategoria.calcular(c, categoria));
        }
        for (Coleccion c : colecciones) {
            resultados.add(provinciaMasHechos.calcular(c));
        }
        for (Coleccion c : colecciones) {
            resultados.add(provinciaPorCategoria.calcular(c, categoria));
        }

        // TODO: resultados.add(spamCalc.calcular(solicitudes));
        estadisticasRepository.saveAll(resultados);
        return resultados; // solo devuelve los resultados
    }

}