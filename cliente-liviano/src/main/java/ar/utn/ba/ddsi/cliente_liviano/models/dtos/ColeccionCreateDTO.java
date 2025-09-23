package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class ColeccionCreateDTO {
    private String titulo;
    private String descripcion;
    private String algoritmo; // MAYORIA_SIMPLE ; MULTIPLES_MENCIONES ; ABSOLUTA
}

