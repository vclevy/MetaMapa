package domain.coleccion;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private List<Hecho> hechos;

    public void agregarHechos(Hecho ... unosHechos){
        Collections.addAll(this.hechos, unosHechos);
    }
}
