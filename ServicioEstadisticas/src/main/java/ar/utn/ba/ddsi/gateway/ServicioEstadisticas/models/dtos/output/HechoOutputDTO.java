package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class HechoOutputDTO {
    private String categoria;
    private String provincia;
    private LocalDateTime timestamp;
}