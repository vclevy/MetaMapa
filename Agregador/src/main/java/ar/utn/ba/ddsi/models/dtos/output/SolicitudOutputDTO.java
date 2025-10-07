package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SolicitudOutputDTO {
    private String justificacion;
    private Long idHecho;
    private String nombreDeUsuario;

    public SolicitudOutputDTO(Solicitud solicitud) {
        this.justificacion = solicitud.getJustificacionDeEliminacion();
        this.idHecho = solicitud.getHecho().getId();
        this.nombreDeUsuario = solicitud.getNombreDeUsuario();
    }
}