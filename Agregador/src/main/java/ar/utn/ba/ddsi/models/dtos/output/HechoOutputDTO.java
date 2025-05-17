package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Solicitud> solicitudesDeEliminacion;
    private List<Etiqueta> etiquetas;
    private List<Multimedia> multimedia;
    private Usuario contribuyente;
}
