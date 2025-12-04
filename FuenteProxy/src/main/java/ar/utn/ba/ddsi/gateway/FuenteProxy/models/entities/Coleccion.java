package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "colecciones") // opcional, si querés darle nombre a la tabla
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    private String handle;

    @OneToMany
    @JoinColumn(name = "coleccion_id")
    private List<Hecho> hechos;

    @Transient
    private List<Criterio> criterioDePertenencia;

    public Coleccion(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
    }
    public Coleccion() {}
}
