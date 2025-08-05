package ar.utn.ba.ddsi.models.entities.hecho.solicitudes;

import ar.utn.ba.ddsi.models.entities.Usuario;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class HistorialSolicitud {
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaModificacion;
    private Usuario administradorModificador;

    public HistorialSolicitud(EstadoDeSolicitudDeEliminacion nuevoEstado, Usuario admin){
        this.estado = nuevoEstado;
        this.administradorModificador=admin;
        this.fechaModificacion=LocalDateTime.now();
    }
}