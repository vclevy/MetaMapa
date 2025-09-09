package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.input.hecho.HechoInputPUTDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hecho")
public class HechoController {
    private final IHechoService hechoService;

    public HechoController(IHechoService hechoService) { this.hechoService = hechoService; }

    @GetMapping
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechos() {
        return ResponseEntity.ok(this.hechoService.obtenerHechos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HechoOutputDTO> modificarHecho(@PathVariable Long id, @RequestBody HechoInputPUTDTO hechoInputDTO) {
        return ResponseEntity.ok(this.hechoService.modificarHecho(id, hechoInputDTO));
    }
}
