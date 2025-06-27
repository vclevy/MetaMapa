package ar.utn.ba.ddsi.models.dtos.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColeccionPatchDTO {
    private String campo;
    private String nuevoValor;
}
