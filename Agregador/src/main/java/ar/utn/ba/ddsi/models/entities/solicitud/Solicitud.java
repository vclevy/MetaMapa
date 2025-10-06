package ar.utn.ba.ddsi.models.entities.solicitud;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @Column(name = "justificacion", columnDefinition = "TEXT", nullable = false)
    private String justificacionDeEliminacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hecho_id", nullable = false)
    private Hecho hecho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDeSolicitudDeEliminacion estado;

    @Column(name = "fecha_de_carga", nullable = false)
    private LocalDateTime fechaDeCargaDeSolicitud;

    @Column(name = "fecha_de_evaluacion")
    private LocalDateTime fechaDeEvaluacionDeSolicitud;

    @Column(name = "nombre_de_usuario")
    private String nombreDeUsuario;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();

    public Solicitud() {

    }
}