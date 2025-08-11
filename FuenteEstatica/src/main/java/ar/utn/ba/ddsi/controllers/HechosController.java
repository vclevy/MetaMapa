package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.services.IHechosServices;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hechos")
public class HechosController {

    private final IHechosServices hechosServices;

    public HechosController(IHechosServices hechosServices) {
        this.hechosServices = hechosServices;
    }

    @GetMapping
    public ResponseEntity<List<HechoOutputDTO>> obtenerTodosLosHechos() {
        try {
            List<HechoOutputDTO> hechos = hechosServices.getHechos();
            return ResponseEntity.ok(hechos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



