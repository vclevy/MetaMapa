package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.solicitudes;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.users.Administrador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HistorialSolicitud {
    private EstadoDeSolicitudDeEliminacion estado;
    private LocalDateTime fechaModificacion;
    private Administrador administradorModificador;

    public HistorialSolicitud(EstadoDeSolicitudDeEliminacion nuevoEstado, Administrador admin){
        this.estado = nuevoEstado;
        this.administradorModificador=admin;
        this.fechaModificacion=LocalDateTime.now();
    }
}