package ar.utn.ba.ddsi.models.entities.usuario;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Usuario {
    private String nombre;
    private Long idUsuario;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.idUsuario = UUID.randomUUID().getMostSignificantBits();
    }
}