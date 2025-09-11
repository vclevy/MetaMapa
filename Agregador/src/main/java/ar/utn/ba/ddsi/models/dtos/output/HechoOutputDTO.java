package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Solicitud> solicitudesDeEliminacion;
    private List<Etiqueta> etiquetas;
    private List<String> multimedia;
    private Usuario contribuyente;
}
