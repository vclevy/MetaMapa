package services.impl;

import models.entities.Hecho;
import models.repositories.impl.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import services.IProxyServices;

import java.util.List;
import java.util.Map;

@@Service
public class ProxyServices implements IProxyServices {

    private final WebClient webClient;

    @Autowired
    public ProxyServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-ddsi.disilab.ar/public/api").build();
    }

    @Autowired
    private ProxyRepository fuenteProxyRepository;

    public List<Hecho> obtenerTodosLosHechos(Map<String, String> filtros) {
        return fuenteProxyRepository.obtenerHechos(filtros);
    }

    public List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros) {
        return fuenteProxyRepository.obtenerHechosDeColeccion(identificador, filtros);
    }

    public Mono<List<Hecho>> obtenerHechosDesdeAPI(Map<String, String> filtros) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/hechos");
                    filtros.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(Hecho.class)
                .collectList(); //mapear la clase a hecho
    }
}

