package ar.utn.ba.ddsi.models.dtos;

import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private Lugar lugar;
    private LocalDateTime fechaDelHecho;

    public HechoInputDTO(String titulo, String descripcion, String categoria, Lugar lugar, LocalDateTime fechaDelHecho) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugar = lugar;
        this.fechaDelHecho = fechaDelHecho;
    }
}
