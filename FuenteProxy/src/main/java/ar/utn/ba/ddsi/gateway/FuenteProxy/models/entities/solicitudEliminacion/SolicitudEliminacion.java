package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.solicitudEliminacion;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter

public class SolicitudEliminacion {

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
    private EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.PENDIENTE;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

}
