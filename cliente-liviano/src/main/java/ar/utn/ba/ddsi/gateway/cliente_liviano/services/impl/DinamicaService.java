package ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.gateway.cliente_liviano.config.MultipartInputResource;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoDinamicaDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoFormDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.Categoria;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.Lugar;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DinamicaService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;


    @Autowired
    public DinamicaService(
            WebClient.Builder webClientBuilder,
            ObjectMapper objectMapper,
            @Value("${gateway.url}") String urlGateway
    ) {
        this.webClient = webClientBuilder
                .baseUrl(urlGateway + "/api/fuente-dinamica")
                .build();

        this.objectMapper = objectMapper;
    }

    public HechoDinamicaDTO crearHecho(HechoFormDTO hechoForm,
                                       List<MultipartFile> archivos) {
        try {
            HechoDinamicaDTO hechoDto = new HechoDinamicaDTO();
            hechoDto.setTitulo(hechoForm.getTitulo());
            hechoDto.setDescripcion(hechoForm.getDescripcion());
            hechoDto.setFechaDeAcontecimiento(hechoForm.getFechaDeAcontecimiento());

            Lugar lugar = new Lugar();
            lugar.setLatitud(hechoForm.getLatitud());
            lugar.setLongitud(hechoForm.getLongitud());
            hechoDto.setLugar(lugar);

            Categoria categoria = new Categoria();
            categoria.setNombre(hechoForm.getCategoriaNombre());
            hechoDto.setCategoria(categoria);

            hechoDto.setNombreDeUsuario(hechoForm.getNombreDeUsuario());
            hechoDto.setEsAnonimo(hechoForm.getEsAnonimo());

            // Convertir a JSON
            String hechoJson = objectMapper.writeValueAsString(hechoDto);

            // Preparar el body multipart
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            MultipartInputResource hechoResource = new MultipartInputResource(
                    hechoJson.getBytes(), "hecho.json"
            );
            body.add("hecho", hechoResource);

            // Agregar archivos multimedia
            if (archivos != null) {
                for (MultipartFile archivo : archivos) {
                    if (!archivo.isEmpty()) {
                        try {
                            MultipartInputResource fileResource = new MultipartInputResource(
                                    archivo.getBytes(),
                                    archivo.getOriginalFilename()
                            );
                            body.add("archivos", fileResource);
                        } catch (Exception e) {
                            System.err.println("Error leyendo archivo " + archivo.getOriginalFilename() + ": " + e.getMessage());
                        }
                    }
                }
            }

            Mono<HechoDinamicaDTO> response = webClient.post()
                    .uri("/hechos")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(body))
                    .retrieve()
                    .bodyToMono(HechoDinamicaDTO.class);

            return response.block();

        } catch (Exception e) {
            System.err.println("Error creando hecho en DinamicaService: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public HechoDinamicaDTO editarHecho(Long id, HechoFormDTO hechoForm, List<MultipartFile> archivos) {
        try {
            // 1️⃣ Construir el DTO a enviar
            HechoDinamicaDTO hechoDto = new HechoDinamicaDTO();
            hechoDto.setTitulo(hechoForm.getTitulo());
            hechoDto.setDescripcion(hechoForm.getDescripcion());
            hechoDto.setFechaDeAcontecimiento(hechoForm.getFechaDeAcontecimiento());

            Lugar lugar = new Lugar();
            lugar.setLatitud(hechoForm.getLatitud());
            lugar.setLongitud(hechoForm.getLongitud());
            hechoDto.setLugar(lugar);

            hechoDto.setNombreDeUsuario(hechoForm.getNombreDeUsuario());
            hechoDto.setEsAnonimo(hechoForm.getEsAnonimo());

            // 2️⃣ Convertir a JSON
            String hechoJson = objectMapper.writeValueAsString(hechoDto);

            // 3️⃣ Preparar multipart
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            MultipartInputResource hechoResource = new MultipartInputResource(
                    hechoJson.getBytes(),
                    "hecho.json"
            );
            body.add("hecho", hechoResource);

            // 4️⃣ Agregar archivos si hay
            if (archivos != null) {
                for (MultipartFile archivo : archivos) {
                    if (!archivo.isEmpty()) {
                        MultipartInputResource fileResource = new MultipartInputResource(
                                archivo.getBytes(),
                                archivo.getOriginalFilename()
                        );
                        body.add("archivos", fileResource);
                    }
                }
            }
            System.out.println("Llega hasta dinamica service");
            // 5️⃣ Llamada al backend Dinámica (PUT /hechos/{id})
            return webClient.put()
                    .uri("/hechos/{id}", id)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(body))
                    .retrieve()
                    .bodyToMono(HechoDinamicaDTO.class)
                    .block();

        } catch (Exception e) {
            System.err.println("Error editando hecho en DinamicaService: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
