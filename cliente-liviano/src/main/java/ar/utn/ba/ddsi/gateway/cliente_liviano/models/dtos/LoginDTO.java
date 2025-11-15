package ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String nombreDeUsuario;
    private String clave;
}
