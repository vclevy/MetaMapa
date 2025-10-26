package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoFiltroDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hecho")
public class HechoController {
    private final IHechoService hechoService;

    public HechoController(IHechoService hechoService) {
        this.hechoService = hechoService;
    }

    @GetMapping
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechos() {
        return ResponseEntity.ok(this.hechoService.obtenerHechos());
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechosPendientes() {
        return ResponseEntity.ok(this.hechoService.obtenerHechosPendientes());
    }

    @GetMapping("/visibles")
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDisponibles() {
        return ResponseEntity.ok(this.hechoService.obtenerHechosVisibles());
    }

    @GetMapping("/destacados")
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDestacados() {
        return ResponseEntity.ok(this.hechoService.obtenerHechosDestacados());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUYENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<HechoOutputDTO> modificarHecho(@PathVariable Long id,
                                                         @RequestBody HechoInputPUTDTO hechoInputDTO) {
        return ResponseEntity.ok(this.hechoService.modificarHecho(id, hechoInputDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<HechoOutputDTO> eliminarHecho(@PathVariable Long id,
                                                         @RequestBody Long hechoId) {
       //TODO
        return null;
    }


    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarHecho(@PathVariable Long id) {
       this.hechoService.aprobarHecho(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/filtrar")
    public ResponseEntity<List<HechoOutputDTO>> filtrarHechos(@RequestBody HechoFiltroDTO filtros) {
        return ResponseEntity.ok(this.hechoService.filtrarHechos(filtros));
    }
}