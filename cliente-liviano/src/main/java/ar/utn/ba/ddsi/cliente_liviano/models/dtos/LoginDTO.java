package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String nombreDeUsuario;
    private String clave;
}
