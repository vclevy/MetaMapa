package ar.utn.ba.ddsi.ServicioEstadisticas.schedulers;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.ColeccionService;
import ar.utn.ba.ddsi.ServicioEstadisticas.services.impl.StatsServices;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsScheduler {

    private final StatsServices statsServices;
    private final ColeccionService coleccionService;

    public StatsScheduler(StatsServices statsServices, ColeccionService coleccionService) {
        this.statsServices = statsServices;
        this.coleccionService = coleccionService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void recalcularEstadisticas() throws Exception {
        List<Coleccion> colecciones = coleccionService.obtenerColecciones();
        String categoria = "Incendio Forestal";
        statsServices.calcularTodas(colecciones);
        System.out.println("Estadísticas recalculadas " + LocalDateTime.now());
    }
}