package ar.utn.ba.ddsi.gateway.models.dtos.output;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;
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
    private List<Etiqueta> etiquetas;
    private List<String> multimedia;
    private String nombreDeUsuario;
    private String fuenteNombre;
    private String nombreArchivo;
    private Boolean esAnonimo;
}
