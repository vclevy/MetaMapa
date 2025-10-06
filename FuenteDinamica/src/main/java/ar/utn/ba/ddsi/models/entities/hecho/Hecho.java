package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.solicitudes.EstadoSolicitudEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitudes.Solicitud;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "fecha_acontecimiento", nullable = false)
    private LocalDateTime fechaDeAcontecimiento;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaDeCarga;

    @Embedded
    private Lugar lugar;

    @Enumerated(EnumType.STRING)
    @Column(name = "origen", nullable = false, length = 50)
    private OrigenDelHecho origen;

    @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    @Column(name = "es_anonimo", nullable = false)
    private Boolean esAnonimo;

    @ManyToMany
    @JoinTable(
            name = "hecho_etiquetas",
            joinColumns = @JoinColumn(name = "hecho_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "hecho_multimedia", joinColumns = @JoinColumn(name = "hecho_id"))
    @Column(name = "url")
    private List<String> multimedia = new ArrayList<>();

    @Column(name = "fue_eliminado", nullable = false)
    private Boolean fueEliminado = false;

    @Column(name = "nombre_de_usuario", length = 100)
    private String nombreDeUsuario;

    @Embedded
    private Revision revision;

    @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModificacionHecho> historialDeModificaciones = new ArrayList<>();

    public void agregarEtiquetas(Etiqueta... unasEtiquetas) {
        Collections.addAll(this.etiquetas, unasEtiquetas);
    }

    public void agregarSolicitudDeEliminacion(Solicitud unaSolicitud) {
        this.solicitudesDeEliminacion.add(unaSolicitud);
    }

    public boolean tieneSolicitudesDeEliminacionAprobada() {
       return solicitudesDeEliminacion.stream().anyMatch(unaSolicitud -> unaSolicitud.getEstado() == EstadoSolicitudEliminacion.APROBADA);
    }

    public boolean esEditable(){
        return (ChronoUnit.DAYS.between(this.getFechaDeCarga(), LocalDateTime.now())) < 7;
    }

    public boolean esAnonimo(){
        return this.nombreDeUsuario == null;
    }
}
