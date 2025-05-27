package ar.utn.ba.ddsi.services.fuentes;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class FuenteEstatica implements FuenteDeHechos {
    private final WebClient webClient;

    public FuenteEstatica(IHechosRepository hechosRepo, String baseUrl, WebClient webClient) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return webClient
                .get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(Hecho.class)
                .collectList()
                .block();
    }
}
