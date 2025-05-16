package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.roles.roleDefinition.IRol;
import lombok.Getter;

import java.util.Set;

@Getter
public class Usuario {
    private String nombre;
    private IRol rol;
}