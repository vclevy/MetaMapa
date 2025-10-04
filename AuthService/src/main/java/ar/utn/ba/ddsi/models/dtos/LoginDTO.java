package ar.utn.ba.ddsi.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String nombreDeUsuario;
    private String clave;
}
