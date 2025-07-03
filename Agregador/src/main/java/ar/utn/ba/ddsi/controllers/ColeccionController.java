package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteDeleteDTO;
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
    public ResponseEntity<List<Hecho>> obtenerHechosDeUnaColeccion(@PathVariable String handle) {
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

    @PatchMapping("/{handle}")
    public ResponseEntity<Void> modificarAtributo(@PathVariable String handle, @RequestBody ColeccionPatchDTO patchDTO) {
        coleccionService.modificarAtributo(handle, patchDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{handle}")
    public ResponseEntity<Void> modificarFuentes(@PathVariable String handle, @RequestBody ColeccionPatchDTO patchDTO) {
        coleccionService.modificarAtributo(handle, patchDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{handle}/fuentes")
    public ResponseEntity<Void> eliminarFuentes(@PathVariable String handle, @RequestBody FuenteDeleteDTO fuenteDTO) {
        coleccionService.eliminarUnaFuenteDeUnaColeccion(handle, fuenteDTO.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{handle}/fuentes")
    public ResponseEntity<Void> crearFuentes(@PathVariable String handle, @RequestBody FuenteCreateDTO fuenteDTO) {
        this.coleccionService.agregarUnaFuenteDeUnaColeccion(handle, fuenteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
