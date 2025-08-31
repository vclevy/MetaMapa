package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.services.IHechosServices;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hechos")
public class HechosController {

    private final IHechosServices hechosServices;

    public HechosController(IHechosServices hechosServices) {
        this.hechosServices = hechosServices;
    }

    @GetMapping
    public ResponseEntity<List<HechoOutputDTO>> obtenerTodosLosHechos() {
        try {
            List<HechoOutputDTO> hechos = hechosServices.getHechos();
            return ResponseEntity.ok(hechos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/archivo")
    public ResponseEntity<String> importarHecho(@RequestPart("archivos") MultipartFile[] archivos) {
        if (archivos == null || archivos.length == 0) {
            return ResponseEntity.badRequest().body("No se subieron archivos");
        }

        try {
            List<String> rutasArchivos = new ArrayList<>();
            String tempDir = System.getProperty("java.io.tmpdir");

            for (MultipartFile archivo : archivos) {
                String ruta = tempDir + File.separator + archivo.getOriginalFilename();
                File destino = new File(ruta);
                archivo.transferTo(destino);
                rutasArchivos.add(ruta);
            }

            hechosServices.importarHechos(rutasArchivos);

            return ResponseEntity.ok("Hechos importados correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al importar los hechos: " + e.getMessage());
        }
    }
}



