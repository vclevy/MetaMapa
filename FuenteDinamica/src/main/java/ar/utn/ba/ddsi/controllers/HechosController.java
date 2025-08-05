package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/hechos")
public class HechosController {

    @Autowired
    private IHechosServices hechosServices;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearHecho(
            @ModelAttribute HechoInputDTO hechoDTO,
            @RequestParam(value = "archivos", required = false) MultipartFile[] archivos
    ) {
        try {
            hechosServices.subirHecho(hechoDTO, archivos);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
