package ar.utn.ba.ddsi.models.entities.coleccion;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private String handle;

    public Coleccion (String titulo, String descripcion, String handle) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = handle;
    }
}
