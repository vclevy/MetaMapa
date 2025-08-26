package ar.utn.ba.ddsi.models.entities.hecho.solicitudes;

import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "justificacion", columnDefinition = "TEXT", nullable = false)
    private String justificacionDeEliminacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hecho_id", nullable = false)
    private Hecho hecho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDeSolicitudDeEliminacion estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_evaluacion")
    private LocalDateTime fechaDeEvaluacionDeSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario visitanteQueCargoLaSolicitud;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();

    public Solicitud() {}

    public Solicitud(Hecho unHecho, String unaJustificacion) {
        this.hecho = unHecho;
        this.justificacionDeEliminacion = unaJustificacion;
    }
}