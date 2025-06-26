package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.coleccionService.IColeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coleccion")
public class ColeccionController {
    private final IColeccionService coleccionService;

    public ColeccionController(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @PostMapping
    public ResponseEntity<Void> crearColeccion(@RequestBody ColeccionInputDTO dto) {
        coleccionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> borrarColeccion(@RequestParam String handle) {
        coleccionService.delete(handle);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{handle}")
    public ResponseEntity<List<Hecho>> obtenerColeccion(@PathVariable String handle) {
        var coleccion = coleccionService.findByHandle(handle);
        if (coleccion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(coleccion.getHechos());
    }

    @GetMapping
    public ResponseEntity<List<Coleccion>> obtenerColecciones() {
        return ResponseEntity.ok(coleccionService.findAll());
    }

    //TODO: Implementar el endpint para modificar colecciones


}
