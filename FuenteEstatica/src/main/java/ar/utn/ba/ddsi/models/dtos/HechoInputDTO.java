package ar.utn.ba.ddsi.models.dtos;

import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private Lugar lugar;
    private LocalDate fechaDelHecho;

    public HechoInputDTO(String titulo, String descripcion, String categoria, Lugar lugar, LocalDate fechaDelHecho) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugar = lugar;
        this.fechaDelHecho = fechaDelHecho;
    }
}
