package ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class SolicitudDTO {
    private Long id;
    private String justificacion;
    private Long idHecho;
    private String nombreDeUsuario;
}
