package ar.utn.ba.ddsi.cliente_liviano.models.entities.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Usuario {
    private Long idUsuario;

    private String nombre;
    private Rol rol;
}