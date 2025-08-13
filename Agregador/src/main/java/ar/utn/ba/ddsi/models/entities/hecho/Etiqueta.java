package ar.utn.ba.ddsi.models.entities.hecho;

public class Etiqueta {
    private String nombre;
    private String descripcion;

    public Etiqueta() {
    }  // Necesario para la deserialización JSON

    public Etiqueta(String nombre) {
        this.nombre = nombre;
        this.descripcion = null;
    }

    public Etiqueta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}