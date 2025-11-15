package ar.utn.ba.ddsi.gateway.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String nombreDeUsuario;
    private String clave;
}
