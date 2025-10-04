package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String AUTH_URL = "http://localhost:8086/api/auth";

    public AuthResponse login(String username, String password) {
        AuthRequest request = new AuthRequest(username, password);
        ResponseEntity<AuthResponse> response =
                restTemplate.postForEntity(AUTH_URL, request, AuthResponse.class);
        return response.getBody();
    }
}
