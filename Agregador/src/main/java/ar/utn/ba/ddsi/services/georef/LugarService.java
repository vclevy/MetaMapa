package ar.utn.ba.ddsi.services.georef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LugarService {

    private final GeorefService georefService;

    @Autowired
    public LugarService(GeorefService georefService) {
        this.georefService = georefService;
    }

    public String obtenerProvincia(Double lat, Double lon) {
        if (lat == null || lon == null) {
            return "Desconocida";
        }
        // Aquí convertimos el Mono<String> a String bloqueando la ejecución
        return georefService.obtenerProvincia(lat, lon).block();
    }
}