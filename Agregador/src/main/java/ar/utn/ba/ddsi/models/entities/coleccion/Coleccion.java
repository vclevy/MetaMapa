package ar.utn.ba.ddsi.models.entities.coleccion;


import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private String handle; // TODO
    private List<Hecho> hechos;

    public Coleccion (String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        // this.handle
    }
}
