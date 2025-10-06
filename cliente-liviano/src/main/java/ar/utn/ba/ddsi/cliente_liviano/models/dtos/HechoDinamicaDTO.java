package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import ar.utn.ba.ddsi.cliente_liviano.models.entities.Categoria;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.Lugar;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Getter
@Setter
public class HechoDinamicaDTO {
        private String titulo;
        private String descripcion;
        private LocalDateTime fechaDeAcontecimiento;
        private Lugar lugar;
        private List<String> multimedia;
        private Categoria categoria;
        private Boolean esAnonimo;
        private String nombreDeUsuario;
}
