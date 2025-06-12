package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.services.coleccionService.IColeccionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coleccion")
public class ColeccionController {
    private final IColeccionService coleccionService;

    public ColeccionController(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }


}
