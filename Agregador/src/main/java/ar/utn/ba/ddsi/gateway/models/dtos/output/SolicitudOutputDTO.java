package ar.utn.ba.ddsi.gateway.models.dtos.output;

import ar.utn.ba.ddsi.gateway.models.entities.solicitud.Solicitud;
import lombok.Data;

@Data
public class SolicitudOutputDTO {
    private String justificacion;
    private Long idHecho;
    private String nombreDeUsuario;
    private Long id;

    public SolicitudOutputDTO(Solicitud solicitud) {
        this.id = solicitud.getIdSolicitud();
        this.justificacion = solicitud.getJustificacionDeEliminacion();
        this.idHecho = solicitud.getHecho().getId();
        this.nombreDeUsuario = solicitud.getNombreDeUsuario();
    }
}