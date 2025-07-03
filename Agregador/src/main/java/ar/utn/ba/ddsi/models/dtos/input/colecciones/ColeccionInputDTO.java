package ar.utn.ba.ddsi.models.dtos.input.colecciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private String algoritmo; // todo: convertirlo a un enum
    private String modoDeNavegacion;
}
