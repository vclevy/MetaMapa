package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.services.IHechosServices;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearHecho(
            @RequestPart("hecho") String hechoJson,
            @RequestPart(value = "archivos", required = false) MultipartFile[] archivos
    ) {
        try {
            HechoInputDTO hechoDTO = objectMapper.readValue(hechoJson, HechoInputDTO.class);
            hechosServices.subirHecho(hechoDTO, archivos);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

