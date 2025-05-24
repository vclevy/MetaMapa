package ar.utn.ba.ddsi.schedulers;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import ar.utn.ba.ddsi.services.hechoService.IHechosService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class AgregadorDeHechos {
    private final IHechosService hechosService;

    public AgregadorDeHechos(List<FuenteDeHechos> fuentes, IHechosService hechosService) {
        this.hechosService = hechosService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void obtenerTodosLosHechosActualizados() {
        this.hechosService.obtenerTodosLosHechosDeTodasLasFuentes();
    }
}










