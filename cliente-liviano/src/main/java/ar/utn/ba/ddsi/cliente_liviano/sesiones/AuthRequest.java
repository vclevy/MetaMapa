package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}