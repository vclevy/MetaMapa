package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class Coleccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    private String handle;
    private List<Hecho> hechos;
    private List<Criterio> criterioDePertenencia;

    public Coleccion (String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
    }
}
