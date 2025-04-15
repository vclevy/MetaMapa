package domain.coleccion;

import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private Map<String, Hecho> hechos;

    public Coleccion() {
        hechos = new HashMap<>();
    }

    public void agregarHecho(Hecho hecho){
        //Collections.addAll(this.hechos, unosHechos);
        hechos.put(hecho.getTitulo(),hecho);
    }

    public void eliminarHecho(Hecho unHecho) {
        this.hechos.remove(unHecho);
    }

}
