package ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities;

import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    // Aquí guardamos el token actual (por sesión de usuario, por ejemplo)
    private String token;

    // Setear el token después del login
    public void setToken(String token) {
        this.token = token;
    }

    // Obtener el token actual
    public String getToken() {
        return token;
    }

    // Limpiar token al hacer logout
    public void clearToken() {
        this.token = null;
    }

    // Verificar si hay un token válido
    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }
}
