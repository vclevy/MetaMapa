package ar.utn.ba.ddsi.models.dtos.input.hecho;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HechoInputEstaticaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDateTime fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private List<String> multimedia;
    private String nombreArchivo;
}
