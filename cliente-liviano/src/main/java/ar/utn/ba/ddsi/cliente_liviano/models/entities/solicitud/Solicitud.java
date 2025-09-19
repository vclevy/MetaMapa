package ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Solicitud {
    private Long idSolicitud;

    private String justificacionDeEliminacion;

    private HechoInputDTO hecho;

    private EstadoDeSolicitudDeEliminacion estado;

    private LocalDateTime fechaDeCargaDeSolicitud;

    private LocalDateTime fechaDeEvaluacionDeSolicitud;

    private Usuario usuarioQueCargoLaSolicitud;

    private List<HistorialSolicitud> historialSolicitud = new ArrayList<>();


    public Solicitud (String unaJustificacion, HechoInputDTO hecho, Usuario unUsuario) {
        this.justificacionDeEliminacion = unaJustificacion;
        this.hecho = hecho;
        this.usuarioQueCargoLaSolicitud = unUsuario;
        this.fechaDeCargaDeSolicitud = LocalDateTime.now();
        this.estado = EstadoDeSolicitudDeEliminacion.PENDIENTE;
    }


    public Solicitud() {

    }
}