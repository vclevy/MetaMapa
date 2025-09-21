package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.IAgregadorService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgregadorService implements IAgregadorService {

    private final WebClient webClient;

    public AgregadorService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public List<HechoInputDTO> obtenerHechos() {
        return webClient.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .collectList()
                .block();
    }

    public List <HechoInputDTO> obtenerHechosDestacados(){ //TODO mejorar la logica esta
        List<HechoInputDTO> hechos = webClient.get()
                .uri("/api/hecho")
                .retrieve()
                .bodyToFlux(HechoInputDTO.class)
                .collectList()
                .block();

        return hechos.stream().limit(3).collect(Collectors.toList());
    }
    public List<HechoInputDTO> obtenerHechosEnBounds(double south, double west, double north, double east) {
        return this.obtenerHechos().stream()
                .filter(h -> h.getLugar().getLatitud() != null &&h.getLugar().getLongitud() != null)
                .filter(h -> h.getLugar().getLatitud() >= south && h.getLugar().getLatitud() <= north)
                .filter(h -> h.getLugar().getLongitud() >= west && h.getLugar().getLongitud() <= east)
                .collect(Collectors.toList());
    }

    public HechoInputDTO obtenerHechoPorId(long id) {
        return obtenerHechos().stream().filter(h -> h.getId() == id).findFirst().orElse(null);
    }

}
