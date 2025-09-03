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
    private final IColeccionService coleccionService;

    public ColeccionController(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @PostMapping
    public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO dto) {
        return ResponseEntity.ok(coleccionService.crear(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> borrarColeccion(@RequestParam Long id) {
        boolean eliminada = coleccionService.delete(id);
        if (eliminada) {
            return ResponseEntity.ok("Colección eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ninguna colección con el handle: " + id);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Hecho>> obtenerHechosDeUnaColeccion(@PathVariable Long id, @RequestParam (defaultValue = "IRRESTRICTO") String modoDeNavegacion) {
        List<Hecho> hechos = coleccionService.obtenerHechosDeColeccionSegunModoDeNavegacion(id, modoDeNavegacion);
        return ResponseEntity.ok(hechos);
    }

    @GetMapping
    public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones() {
        return ResponseEntity.ok(coleccionService.findAll());
    }

    @PatchMapping("/{id}/atributo")
    public ResponseEntity<ColeccionOutputDTO> modificarAtributo(@PathVariable Long id, @RequestBody ColeccionPatchDTO patchDTO) {
        return ResponseEntity.ok(this.coleccionService.modificarAtributo(id, patchDTO));
    }

    @PostMapping("/{id}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> agregarFuentes(@PathVariable Long id, @RequestBody FuenteCreateDTO fuenteDTO) {
        return ResponseEntity.ok(this.coleccionService.agregarUnaFuenteDeUnaColeccion(id, fuenteDTO));
    }

    @DeleteMapping("/{id}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> eliminarFuentes(@PathVariable Long id, @RequestBody FuenteDeleteDTO fuenteDTO) {
        return ResponseEntity.ok(coleccionService.eliminarUnaFuenteDeUnaColeccion(id, fuenteDTO.getHandleDeFuente()));
    }

    @GetMapping("/{id}/fuentes")
    public ResponseEntity<List<FuenteDeHechoOutputDTO>> obtenerFuentesDeUnaColeccion(@PathVariable Long id) {
        List<FuenteDeHechoOutputDTO> fuentes = coleccionService.obtenerFuentesDeUnaColeccion(id);
        return ResponseEntity.ok(fuentes);
    }
}