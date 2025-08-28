package ar.utn.ba.ddsi.models.entities.hecho.solicitudes;

import ar.utn.ba.ddsi.models.entities.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_solicitudes")
@Getter
@Setter
public class HistorialSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitudEliminacion estado;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id")
    private Usuario administradorModificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    public HistorialSolicitud() {}

    public HistorialSolicitud(EstadoSolicitudEliminacion nuevoEstado, Usuario admin) {
        this.estado = nuevoEstado;
        this.administradorModificador = admin;
        this.fechaModificacion = LocalDateTime.now();
    }
}
