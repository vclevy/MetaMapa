package ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstaticaService {

    private WebClient webClient;
    @Value("${gateway.url}") String urlGateway;
    private ObjectMapper objectMapper;

    public EstaticaService(
            WebClient.Builder webClientBuilder,
            @Value("${gateway.url}") String urlGateway
    ) {
        this.webClient = webClientBuilder
                .baseUrl(urlGateway + "/api/fuente-estatica")
                .build();
    }

    public Mono<String> importarArchivo(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Archivo vacío"));
        }

        // Construir body multipart
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("archivos", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        }).contentType(MediaType.MULTIPART_FORM_DATA);

        return webClient.post()
                .uri("/hechos/archivo")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class); // acá podes mapear al tipo que devuelva el endpoint
    }

    public Mono<String> importarArchivos(MultipartFile[] files) {
        if(files == null || files.length == 0){
            return Mono.error(new IllegalArgumentException("No se subieron archivos"));
        }

        List<Mono<String>> monos = Arrays.stream(files)
                .map(file -> {
                    try {
                        return importarArchivo(file); // Mono<String>
                    } catch (IOException e) {
                        return Mono.<String>error(e); // forzar tipo a Mono<String>
                    }
                })
                .collect(Collectors.toList());

        return Mono.when(monos)
                .then(Mono.just("Todos los archivos importados correctamente"));
    }


    public void editarHechoEstatica(Long id, HechoInputDTO hechoInput) {
        try {
            // 1️⃣ Convertir el DTO a JSON (opcional, WebClient lo hace automáticamente)
            String hechoJson = objectMapper.writeValueAsString(hechoInput);

            // 2️⃣ Llamada al backend Estática (PUT /hechos/{id})
            webClient.put()
                    .uri("/hechos/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(hechoJson)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (Exception e) {
            System.err.println("Error editando hecho en EstaticaService: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
