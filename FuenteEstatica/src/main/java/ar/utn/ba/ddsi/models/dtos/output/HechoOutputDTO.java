package ar.utn.ba.ddsi.models.dtos.output;


import ar.utn.ba.ddsi.models.entities.Etiqueta;
import ar.utn.ba.ddsi.models.entities.Lugar;
import ar.utn.ba.ddsi.models.hecho.Categoria;
import ar.utn.ba.ddsi.models.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.models.hecho.solicitudes.Solicitud;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data

@Getter
@Setter
public class HechoOutputDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private List<Etiqueta> etiquetas;
    private Boolean fueEliminado = false;
}
