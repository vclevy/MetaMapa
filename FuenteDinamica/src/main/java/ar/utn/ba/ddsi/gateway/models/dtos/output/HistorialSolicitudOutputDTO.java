package ar.utn.ba.ddsi.gateway.models.dtos.output;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialSolicitudOutputDTO {
    private Integer id;
    private String estado;
    private LocalDateTime fechaModificacion;
    private String administrador;

}
