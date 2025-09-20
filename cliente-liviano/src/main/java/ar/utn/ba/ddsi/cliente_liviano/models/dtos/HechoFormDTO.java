package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoFormDTO {
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private String ubicacion;
}