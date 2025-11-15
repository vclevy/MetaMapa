package ar.utn.ba.ddsi.gateway.schedulers;

import ar.utn.ba.ddsi.gateway.services.coleccionService.IColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionesScheduler {

    private final IColeccionService coleccionService;

    public ColeccionesScheduler(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refrescar() {
        this.coleccionService.refrescarColecciones();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void aplicarAlgoritmos() {
        this.coleccionService.aplicarAlgoritmosAColecciones();
    }
}
