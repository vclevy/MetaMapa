package ar.utn.ba.ddsi.gateway.services.georef;

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
        return georefService.obtenerProvincia(lat, lon);
    }
}
