package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Multimedia;
import ar.utn.ba.ddsi.models.entities.hecho.OrigenDelHecho;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data

@Getter
@Setter
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private Categoria categoriaId;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen; // no se si iria
    private List<Etiqueta> etiquetas;
    private List<Multimedia> multimedia;
    private Usuario contribuyente;
}
