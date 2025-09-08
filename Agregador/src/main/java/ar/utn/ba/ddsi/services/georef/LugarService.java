package ar.utn.ba.ddsi.services.georef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LugarService {

    private final GeorefService georefService;

    @Autowired
    public LugarService(GeorefService georefService) {
        this.georefService = georefService;
    }

    public Mono<Object> obtenerProvincia(Double lat, Double lon) {
        if (lat == null || lon == null) {
            return Mono.just("Desconocida");
        }
        return georefService.obtenerProvincia(lat, lon);
    }
}