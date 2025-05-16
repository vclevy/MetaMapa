package ar.utn.ba.ddsi.models.entities;

import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import lombok.Getter;

@Getter
public class Usuario {
    private String nombre;
    private IRol rol;
}