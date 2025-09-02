package ar.utn.ba.ddsi.models.entities.solicitud;

import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity
@Table(name = "historial_solicitudes")
@Setter
@Getter
public class HistorialSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDeSolicitudDeEliminacion estado;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioModificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    public HistorialSolicitud(EstadoDeSolicitudDeEliminacion nuevoEstado, Usuario admin){
        this.estado = nuevoEstado;
        this.usuarioModificador=admin;
        this.fechaModificacion=LocalDateTime.now();
    }

    public HistorialSolicitud() {

    }
}