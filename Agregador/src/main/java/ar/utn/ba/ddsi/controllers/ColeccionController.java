package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos.FuenteDeleteDTO;
import ar.utn.ba.ddsi.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.coleccionService.IColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/coleccion")
public class ColeccionController {
    @Autowired
    private final IColeccionService coleccionService;

    public ColeccionController(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @PostMapping
    public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO dto) {
        return ResponseEntity.ok(coleccionService.crear(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> borrarColeccion(@RequestParam String handle) {
        boolean eliminada = coleccionService.delete(handle);
        if (eliminada) {
            return ResponseEntity.ok("Colección eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ninguna colección con el handle: " + handle);
        }
    }

    @GetMapping("/{handle}")
    public ResponseEntity<List<Hecho>> obtenerHechosDeUnaColeccion(@PathVariable String handle, @RequestParam (defaultValue = "IRRESTRICTO") String modoDeNavegacion) {
        List<Hecho> hechos = coleccionService.obtenerHechosDeColeccionSegunModoDeNavegacion(handle, modoDeNavegacion);
        return ResponseEntity.ok(hechos);
    }

    @GetMapping
    public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones() {
        return ResponseEntity.ok(coleccionService.findAll());
    }

    @PatchMapping("/{handle}/atributo")
    public ResponseEntity<ColeccionOutputDTO> modificarAtributo(@PathVariable String handle, @RequestBody ColeccionPatchDTO patchDTO) {
        return ResponseEntity.ok(this.coleccionService.modificarAtributo(handle, patchDTO));
    }

    @PostMapping("/{handle}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> agregarFuentes(@PathVariable String handle, @RequestBody FuenteCreateDTO fuenteDTO) {
        return ResponseEntity.ok(this.coleccionService.agregarUnaFuenteDeUnaColeccion(handle, fuenteDTO));
    }

    @DeleteMapping("/{handle}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> eliminarFuentes(@PathVariable String handle, @RequestBody FuenteDeleteDTO fuenteDTO) {
        return ResponseEntity.ok(coleccionService.eliminarUnaFuenteDeUnaColeccion(handle, fuenteDTO.getHandleDeFuente()));
    }

    @GetMapping("/{handle}/fuentes")
    public ResponseEntity<List<FuenteDeHechoOutputDTO>> obtenerFuentesDeUnaColeccion(@PathVariable String handle) {
        List<FuenteDeHechoOutputDTO> fuentes = coleccionService.obtenerFuentesDeUnaColeccion(handle);
        return ResponseEntity.ok(fuentes);
    }
}