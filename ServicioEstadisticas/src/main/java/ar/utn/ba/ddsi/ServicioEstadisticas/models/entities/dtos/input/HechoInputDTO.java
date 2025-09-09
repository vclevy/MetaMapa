package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Lugar;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
    private String titulo;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
}

