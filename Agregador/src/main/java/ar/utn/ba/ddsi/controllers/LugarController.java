package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.services.georef.LugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/lugares")
public class LugarController {

    private final LugarService lugarService;

    @Autowired
    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    /**
     * Endpoint POST para obtener un objeto Lugar con latitud, longitud y provincia.
     * Ejemplo de JSON en Postman:
     * {
     *     "latitud": -34.61,
     *     "longitud": -58.38
     * }
     */
    @PostMapping("/provincia")
    public String obtenerProvincia(@RequestBody Lugar lugar) {
        // Devuelve directamente el nombre de la provincia
        return lugarService.obtenerProvincia(lugar.getLatitud(), lugar.getLongitud());
    }

}
