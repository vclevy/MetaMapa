package ar.utn.ba.ddsi.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ar.utn.ba.ddsi.services.coleccionService.ColeccionService;

@Component
public class RefrescadorDeColecciones {

    private final ColeccionService coleccionService;

    public RefrescadorDeColecciones(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @Scheduled(fixedRate = 3600000) // cada 1 hora
    public void refrescar() {
        coleccionService.actualizarColecciones();
    }
}
