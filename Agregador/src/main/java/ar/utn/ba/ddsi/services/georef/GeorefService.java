package ar.utn.ba.ddsi.services.georef;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeorefService {

    private final WebClient webClient;

    public GeorefService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://apis.datos.gob.ar/georef/api")
                .build();
    }
    
    public Mono<Object> obtenerProvincia(Double lat, Double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ubicacion")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .build())
                .retrieve()
                .bodyToMono(GeorefResponse.class)
                .map(response -> {
                    if (response != null && response.getUbicacion() != null
                            && response.getUbicacion().getProvincia() != null) {
                        return response.getUbicacion().getProvincia();
                    }
                    return "Provincia desconocida";
                });
    }
}
