package ar.utn.ba.ddsi.services.fuentes;

import java.util.List;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class FuenteDinamica implements FuenteDeHechos {
    private final IHechosRepository hechosRepositoryFuenteDinamica; // hechos de fuente dinamica
    private final WebClient webClient;

    @Override
    public List<Hecho> obtenerHechos() {
        return hechosRepositoryFuenteDinamica.findAll();
    }

    public FuenteDinamica(IHechosRepository hechosRepo, @Value("") String baseUrl /*va en properties el valueWebClient*/, WebClient webClient) {
        this.hechosRepositoryFuenteDinamica = hechosRepo;
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

}