package ar.utn.ba.ddsi.models.dtos.input.colecciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColeccionPatchDTO {
    private String campo;
    private String nuevoValor;
}
