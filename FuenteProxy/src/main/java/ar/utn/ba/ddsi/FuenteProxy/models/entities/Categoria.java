package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Categoria {

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}