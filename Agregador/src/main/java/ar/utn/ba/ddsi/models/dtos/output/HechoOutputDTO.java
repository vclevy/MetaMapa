package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Solicitud> solicitudesDeEliminacion;
    private List<Etiqueta> etiquetas;
    private List<String> multimedia;
    private String nombreDeUsuario;
    private String fuenteNombre;
    private String nombreArchivo;
}
