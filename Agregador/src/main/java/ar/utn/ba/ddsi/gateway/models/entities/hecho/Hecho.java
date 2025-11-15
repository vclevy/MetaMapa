package ar.utn.ba.ddsi.gateway.models.entities.hecho;

import ar.utn.ba.ddsi.gateway.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.Solicitud;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Entity
@Table(name = "hecho")
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_en_fuente", nullable = false)
    private Long idEnFuente;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "fecha_acontecimiento", nullable = false)
    private LocalDateTime fechaDeAcontecimiento;

    @Column(name = "fecha_de_carga", nullable = false)
    private LocalDateTime fechaDeCargaDelHecho;

    @Embedded
    private Lugar lugar;

    @ElementCollection
    @CollectionTable(name = "hecho_multimedia", joinColumns = @JoinColumn(name = "hecho_id"))
    private List<String> multimedia;

    @Column(name = "nombre_de_usuario", length = 100)
    private String nombreDeUsuario;

    @Column(name = "nombre_archivo", length = 255)
    private String nombreArchivo;

    @ManyToMany
    @JoinTable(
            name = "hecho_etiquetas",
            joinColumns = @JoinColumn(name = "hecho_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "origen", nullable = false, length = 50)
    private OrigenDelHecho origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuente_id", nullable = false)
    private Fuente fuente;

    @Column(name = "es_anonimo", nullable = false)
    private Boolean esAnonimo;

    @Column(name = "fue_eliminado", nullable = false)
    private Boolean fueEliminado = false;

    @Column(name= "pendiente", nullable = false)
    private Boolean pendiente = true;

    @Column(name="fue_aceptado", nullable = false)
    private Boolean fueAceptado = false;

    @Column(name = "esta_consensuado", nullable = false)
    private Boolean estaConsensuado = false;

    @PrePersist
    public void prePersist() {
        if (fechaDeCargaDelHecho == null) {
            ZoneId zonaUTC3 = ZoneOffset.ofHours(-3);
            fechaDeCargaDelHecho = ZonedDateTime.now(zonaUTC3).toLocalDateTime();
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hecho)) return false;
        Hecho hecho = (Hecho) o;

        // Acá definís qué hace que dos Hecho sean "el mismo hecho"
        return Objects.equals(titulo, hecho.titulo) &&
                Objects.equals(descripcion, hecho.descripcion) &&
                Objects.equals(fechaDeAcontecimiento, hecho.fechaDeAcontecimiento) &&
                Objects.equals(lugar, hecho.lugar) &&
                Objects.equals(categoria, hecho.categoria);
    }

    public int hashCode() {
        return Objects.hash(titulo, descripcion, fechaDeAcontecimiento, lugar, categoria);
    }
}
