package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
    private List<String> multimedia;
    private Categoria categoria;
    private String nombreDeUsuario;
    private Boolean esAnonimo;
}
