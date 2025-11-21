package ar.utn.ba.ddsi.gateway.controllers;

import ar.utn.ba.ddsi.gateway.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.HechoFiltroDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.gateway.services.hechoService.IHechoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hecho")
public class HechoController {
    private final IHechoService hechoService;
    private final HandlerMapping resourceHandlerMapping;

    public HechoController(IHechoService hechoService, HandlerMapping resourceHandlerMapping) {
        this.hechoService = hechoService;
        this.resourceHandlerMapping = resourceHandlerMapping;
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
    public ResponseEntity<HechoOutputDTO> eliminarHecho(@PathVariable Long id) {

        this.hechoService.rechazarHecho(id);
        return ResponseEntity.ok().build();
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

    @GetMapping("/{id}")
    public ResponseEntity<HechoOutputDTO> obtenerHechoPorId(@PathVariable Long id) {
        try {
            HechoOutputDTO hechoDTO = hechoService.obtenerHechoPorId(id);
            return ResponseEntity.ok(hechoDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}