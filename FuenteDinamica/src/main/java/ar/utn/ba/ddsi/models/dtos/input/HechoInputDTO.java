package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.Multimedia;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Data

@Getter
@Setter
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Etiqueta> etiquetas;
    private List<Multimedia> multimedia;
}
