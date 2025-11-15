package ar.utn.ba.ddsi.gateway.controllers;

import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones.ColeccionPatchDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.fuentesDeHechos.FuenteCreateDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.input.fuentesDeHechos.FuenteDeleteDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.ColeccionOutputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.FuenteDeHechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.services.coleccionService.IColeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/coleccion")
public class ColeccionController {

    private final IColeccionService coleccionService;

    public ColeccionController(IColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @GetMapping
    public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones() {
        return ResponseEntity.ok(coleccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColeccionOutputDTO> obtenerColeccion(@PathVariable Long id) {
        return ResponseEntity.ok(coleccionService.obtenerColeccion(id));
    }

    @GetMapping("/destacadas")
    public ResponseEntity<List<ColeccionOutputDTO>> obtenerColeccionesDestacadas() {
        return ResponseEntity.ok(coleccionService.obtenerColeccionesDestacadas());
    }

    @GetMapping("/{id}/hechos")
    public ResponseEntity<List<Hecho>> obtenerHechosDeUnaColeccion(
            @PathVariable Long id,
            @RequestParam(defaultValue = "IRRESTRICTO") String modoDeNavegacion) {
        return ResponseEntity.ok(
                coleccionService.obtenerHechosDeColeccionSegunModoDeNavegacion(id, modoDeNavegacion)
        );
    }

    @GetMapping("/{id}/fuentes")
    public ResponseEntity<List<FuenteDeHechoOutputDTO>> obtenerFuentesDeUnaColeccion(@PathVariable Long id) {
        return ResponseEntity.ok(coleccionService.obtenerFuentesDeUnaColeccion(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO dto) {
        return ResponseEntity.ok(coleccionService.crear(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> borrarColeccion(@RequestParam Long id) {
        boolean eliminada = coleccionService.delete(id);
        if (eliminada) {
            return ResponseEntity.ok("Colección eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ninguna colección con el id: " + id);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/atributo")
    public ResponseEntity<ColeccionOutputDTO> modificarAtributo(@PathVariable Long id, @RequestBody ColeccionPatchDTO patchDTO) {
        return ResponseEntity.ok(this.coleccionService.modificarAtributo(id, patchDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> agregarFuentes(@PathVariable Long id, @RequestBody FuenteCreateDTO fuenteDTO) {
        return ResponseEntity.ok(this.coleccionService.agregarUnaFuenteDeUnaColeccion(id, fuenteDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idColeccion}/fuentes")
    public ResponseEntity<ColeccionOutputDTO> eliminarFuentes(@PathVariable Long idColeccion, @RequestBody FuenteDeleteDTO fuenteDTO) {
        return ResponseEntity.ok(coleccionService.eliminarUnaFuenteDeUnaColeccion(idColeccion, fuenteDTO.getIdFuente()));
    }
}