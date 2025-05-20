package ar.utn.ba.ddsi.models.entities.hecho.solicitud;

import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HistorialSolicitud {
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaModificacion;
    private Usuario usuarioModificador;

    public HistorialSolicitud(EstadoDeSolicitudDeEliminacion nuevoEstado, Usuario admin){
        this.estado = nuevoEstado;
        this.usuarioModificador=admin;
        this.fechaModificacion=LocalDateTime.now();
    }
}