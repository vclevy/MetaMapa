package ar.utn.ba.ddsi.models.entities;

import ar.utn.ba.ddsi.models.entities.roles.Rol;
import lombok.Getter;

import java.util.Set;

@Getter
public class Usuario {
    private String nombre;
    private Rol rol;
}
