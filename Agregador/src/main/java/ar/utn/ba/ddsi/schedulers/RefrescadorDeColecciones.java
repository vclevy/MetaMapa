package ar.utn.ba.ddsi.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ar.utn.ba.ddsi.services.coleccionService.ColeccionService;

@Component
public class RefrescadorDeColecciones {

    private final ColeccionService coleccionService;

    public RefrescadorDeColecciones(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refrescar() {
        coleccionService.actualizarColecciones();
    }
}
