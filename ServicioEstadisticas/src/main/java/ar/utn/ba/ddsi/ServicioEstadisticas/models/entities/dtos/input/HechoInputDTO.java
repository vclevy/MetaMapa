package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Lugar;
import lombok.Data;

import java.time.LocalDate;
@Data
public class HechoInputDTO {
    private String titulo;
    private String categoriaNombre;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
}

