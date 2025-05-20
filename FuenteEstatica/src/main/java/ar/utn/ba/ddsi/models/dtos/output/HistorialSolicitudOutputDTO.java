package ar.utn.ba.ddsi.models.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialSolicitudOutputDTO {
    private String estado;
    private LocalDateTime fechaModificacion;
    private String administrador;

}
