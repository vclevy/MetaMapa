package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Hecho {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private Boolean esAnonimo = true;
    private List<Etiqueta> etiquetas;
    private List<Multimedia> multimedia;
    private Boolean fueEliminado = false;
    private Usuario contribuyente;
    private Revision revision;
}
