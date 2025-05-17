package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Multimedia> multimedia;
}
