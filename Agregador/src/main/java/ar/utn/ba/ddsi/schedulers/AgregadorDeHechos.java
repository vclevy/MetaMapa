package ar.utn.ba.ddsi.schedulers;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import ar.utn.ba.ddsi.services.hechoService.IHechosService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class AgregadorDeHechos {
    private final List<FuenteDeHechos> fuentes;
    private final IHechosService hechosService;

    public AgregadorDeHechos(List<FuenteDeHechos> fuentes, IHechosService hechosService) {
        this.fuentes = fuentes;
        this.hechosService = hechosService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public List<Hecho> obtenerTodosLosHechos() {
        return fuentes.stream()
                .flatMap(f -> f.obtenerHechos().stream())
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void actualizarHechosDeTodasLasFuentes() {
        this.hechosService.actualizarHechosDeTodasLasFuentes();
    }
}