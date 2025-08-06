package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ModificacionHecho {

    private LocalDateTime fecha;
    private Usuario editor; // nulleable
    private Hecho hechoAnterior;

}
