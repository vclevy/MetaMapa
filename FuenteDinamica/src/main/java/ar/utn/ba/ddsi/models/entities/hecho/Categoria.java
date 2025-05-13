package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.entities.hecho;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Categoria {

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
