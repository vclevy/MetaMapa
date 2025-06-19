package ar.utn.ba.ddsi.models.dtos.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HechoInputProxyDTO {
    private int id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private LocalDateTime fechaHecho;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double latitud;
    private double longitud;
}
