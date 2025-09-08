package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.fuentes.Fuente;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    private LocalDate fechaDeAcontecimiento;

    @Column(name = "fecha_de_carga", nullable = false)
    private LocalDateTime fechaDeCargaDelHecho;

    @Embedded
    private Lugar lugar;

    @ElementCollection
    @CollectionTable(name = "hecho_multimedia", joinColumns = @JoinColumn(name = "hecho_id"))
    private List<String> multimedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_contribuyente_id")
    private Usuario usuarioContribuyente;

    @ManyToMany
    @JoinTable(
            name = "hecho_etiquetas",
            joinColumns = @JoinColumn(name = "hecho_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "origen", nullable = false, length = 50)
    private OrigenDelHecho origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuente_id", nullable = false)
    private Fuente fuente;

    @Column(name = "es_anonimo", nullable = false)
    private Boolean esAnonimo = true;

    @Column(name = "fue_eliminado", nullable = false)
    private Boolean fueEliminado = false;

    @PrePersist
    public void prePersist() {
        if (fechaDeCargaDelHecho == null) {
            fechaDeCargaDelHecho = LocalDateTime.now();
        }
    }
}
