package ar.utn.ba.ddsi.services.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class FuenteDinamica implements FuenteDeHechos {
    private final IHechosRepository hechosRepo; // hechos de fuente dinamica
    private final WebClient webClient;

    public FuenteDinamica(IHechosRepository hechosRepo, WebClient webClient) {
        this.hechosRepo = hechosRepo;
        this.webClient = webClient;
    }

    @Override
    public List<Hecho> obtenerHechos() {
        return hechosRepo.findAll();
    }

    public FuenteDinamica(
            IHechosRepository hechosRepo,
            @Value("") String baseUrl, //va en properties el value
            WebClient webClients
    ) {
        this.hechosRepo= hechosRepo;
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

}