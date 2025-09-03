package ar.utn.ba.ddsi.models.entities.solicitud;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario visitanteQueCargoLaSolicitud;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();


    public Solicitud (String unaJustificacion, Long idHecho, Usuario unUsuario) {
        this.justificacionDeEliminacion = unaJustificacion;
        //this.hecho = new IHechosRepository().findById(idHecho); // TODO: HABLAR CON ALAN
        this.visitanteQueCargoLaSolicitud = unUsuario;
        this.fechaDeCargaDeSolicitud = LocalDateTime.now();
    }


    public Solicitud() {

    }
}