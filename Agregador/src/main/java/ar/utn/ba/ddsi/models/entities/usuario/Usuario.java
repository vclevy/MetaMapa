package ar.utn.ba.ddsi.models.entities.usuario;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Usuario {
    private String nombre;
    private Integer idUsuario;

    public Usuario(String nombre) {
        this.idUsuario = UUID.randomUUID().hashCode();
    }
}