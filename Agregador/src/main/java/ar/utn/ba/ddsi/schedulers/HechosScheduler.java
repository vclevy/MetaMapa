package ar.utn.ba.ddsi.schedulers;

import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import ar.utn.ba.ddsi.services.hechoService.IHechosService;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class HechosScheduler {
    private final IHechosService hechosService;

    public HechosScheduler(List<FuenteDeHechos> fuentes, IHechosService hechosService) {
        this.hechosService = hechosService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void obtenerTodosLosHechosActualizados() {
        hechosService.obtenerTodosLosHechosDeTodasLasFuentes();
    }
}










