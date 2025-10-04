package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

@Data
public class FuenteCreateDTO {
    private String nombre;
    private String tipo;
    private String urlBase;
}