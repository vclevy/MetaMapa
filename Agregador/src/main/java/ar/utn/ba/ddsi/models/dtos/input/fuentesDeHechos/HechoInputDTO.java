package ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos;

import ar.utn.ba.ddsi.models.entities.hecho.*;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class HechoInputDTO {
    private Long idEnFuente;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private double longitud;
    private double latitud;
    private LocalDate fechaAcontecimiento;
    private LocalDateTime fechaDeCargaDelHecho;
    private List<String> multimedia;
    private Usuario usuario;
}
