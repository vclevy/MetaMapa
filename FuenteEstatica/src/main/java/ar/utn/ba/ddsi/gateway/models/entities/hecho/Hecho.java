package ar.utn.ba.ddsi.gateway.models.entities.hecho;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hechos")
public class Hecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "fecha_acontecimiento", nullable = false)
    private LocalDateTime fechaDeAcontecimiento;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaDeCarga;

    @Embedded
    private Lugar lugar;

    @Enumerated(EnumType.STRING)
    private OrigenDelHecho origen;

    @ElementCollection
    @CollectionTable(name = "hecho_multimedia", joinColumns = @JoinColumn(name = "hecho_id"))
    @Column(name = "url")
    private List<String> multimedia;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    public Hecho(String titulo, String descripcion, LocalDateTime fechaDeAcontecimiento, Lugar lugar, String nombreArchivo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = LocalDateTime.now();
        this.lugar = lugar;
        this.origen = OrigenDelHecho.DATASET;
        this.multimedia = new ArrayList<>();
        this.nombreArchivo = nombreArchivo;
    }
}
