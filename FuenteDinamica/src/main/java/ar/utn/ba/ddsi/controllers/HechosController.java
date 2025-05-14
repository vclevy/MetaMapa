package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/hechos")
public class HechosController {
    private IHechosServices hechosServices;

    @PostMapping
    public ResponseEntity<?> crearHecho(@RequestBody HechoInputDTO request, Usuario usuario) {
        hechosServices.subirHecho(request,usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


