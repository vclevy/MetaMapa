package ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.LoginDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.RegistroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090/api/auth").build(); // URL de tu AuthService real
    }

    public Map<String, Object> registrar(RegistroForm registroForm) {
        return webClient.post()
                .uri("/api/auth/register") // endpoint del AuthService
                .bodyValue(registroForm)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public String login(String usuario, String clave) {
        LoginDTO loginRequest = new LoginDTO();
        loginRequest.setNombreDeUsuario(usuario);
        loginRequest.setClave(clave);

        Map<String, Object> response = webClient.post()
                .uri("/api/auth/login") // apunta al AuthService real
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response != null && response.get("token") != null) {
            return (String) response.get("token");
        } else {
            return null;
        }
    }



}