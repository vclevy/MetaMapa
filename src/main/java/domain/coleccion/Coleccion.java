package domain.coleccion;

import domain.hecho.Hecho;
import lombok.Getter;
import java.util.*;

@Getter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private List<Hecho> hechos;

    public Coleccion(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hechos = new ArrayList<>();
    }

    public void agregarHechos(Hecho ... unosHechos){
        Collections.addAll(this.hechos, unosHechos);
    }

    public void eliminarHecho(Hecho unHecho) {
        this.hechos.remove(unHecho);
    }


}
