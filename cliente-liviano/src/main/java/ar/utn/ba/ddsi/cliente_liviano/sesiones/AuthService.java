package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.LoginDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.RegistroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8086")
                .build();
    }

    public Map<String, Object> registrar(RegistroForm registroForm) {
        return webClient.post()
                .uri("/api/auth/register")
                .bodyValue(registroForm)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public AuthResponse login(String usuario, String clave) {
        LoginDTO loginRequest = new LoginDTO();
        loginRequest.setNombreDeUsuario(usuario);
        loginRequest.setClave(clave);

        return webClient.post()
                .uri("/api/auth/login")
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block(); // devuelve accessToken, refreshToken y roles
    }
}
