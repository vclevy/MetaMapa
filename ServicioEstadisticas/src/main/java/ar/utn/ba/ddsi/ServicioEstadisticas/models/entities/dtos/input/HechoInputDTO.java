package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Lugar;

import java.time.LocalDate;
import java.util.List;

public class HechoInputDTO {
    private String titulo;
    private String categoriaNombre;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Solicitud> solicitudesDeEliminacion;
}

