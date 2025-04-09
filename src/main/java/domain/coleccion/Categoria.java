package domain.coleccion;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Categoria {
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;

}
