package ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities;
import lombok.Data;

@Data
public class Etiqueta {
    private Long id;
    private String nombre;
    private String descripcion;

    public Etiqueta() {}

    public Etiqueta(String nombre) {
        this.nombre = nombre;
        this.descripcion = null;
    }

    public Etiqueta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}