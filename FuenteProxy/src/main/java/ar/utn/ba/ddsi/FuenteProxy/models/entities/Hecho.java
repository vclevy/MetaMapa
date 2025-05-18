package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hecho {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDateTime fechaHecho;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double latitud;
    private double longitud;
}
