package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class EstaticaService {

    private WebClient webClient;

    public EstaticaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
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

}
