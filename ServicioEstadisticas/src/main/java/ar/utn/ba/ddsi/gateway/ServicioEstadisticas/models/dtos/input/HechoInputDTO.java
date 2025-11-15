package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.dtos.input;

import ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities.Lugar;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
    private String titulo;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
}

