package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hechos")
public class HechosController {
    private IHechosServices hechosServices;

    @PostMapping(value = "/con-multimedia", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearHechoConImagen(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaDeAcontecimiento") String fechaStr,
            @RequestParam("lugar") String lugar,
            @RequestParam("etiquetas") List<String> etiquetas,
            @RequestParam("imagen") MultipartFile imagen
    ) {
        try {
            Usuario usuario = null; // reemplazá por tu lógica real

            hechosServices.subirHechoConMultimedia(
                    titulo,
                    descripcion,
                    fechaStr,
                    lugar,
                    etiquetas,
                    imagen,
                    usuario
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}


