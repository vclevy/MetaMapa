package ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.solicitud;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HistorialSolicitud {


    private Long id;

    private EstadoDeSolicitudDeEliminacion estado;

    private LocalDateTime fechaModificacion;

    private Usuario usuarioModificador;

    private Solicitud solicitud;

    public HistorialSolicitud(EstadoDeSolicitudDeEliminacion nuevoEstado, Usuario admin){
        this.estado = nuevoEstado;
        this.usuarioModificador=admin;
        this.fechaModificacion=LocalDateTime.now();
    }

    public HistorialSolicitud() {

    }
}