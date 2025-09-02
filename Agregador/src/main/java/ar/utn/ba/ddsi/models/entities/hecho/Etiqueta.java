package ar.utn.ba.ddsi.models.entities.hecho;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
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