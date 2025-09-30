package ar.utn.ba.ddsi.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.RegistroForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8086").build(); // URL de tu AuthService real
    }

    public Map<String, Object> registrar(RegistroForm registroForm) {
        return webClient.post()
                .uri("/api/auth/register") // endpoint del AuthService
                .bodyValue(registroForm)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}