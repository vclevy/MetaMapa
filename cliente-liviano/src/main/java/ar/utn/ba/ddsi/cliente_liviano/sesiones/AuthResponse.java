package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}