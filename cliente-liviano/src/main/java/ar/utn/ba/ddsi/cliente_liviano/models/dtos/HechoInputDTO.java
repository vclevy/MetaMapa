package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import ar.utn.ba.ddsi.cliente_liviano.models.entities.Lugar;
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

    // Constructor correcto para Estática
    public HechoInputDTO(String titulo, String descripcion, Lugar lugar, LocalDateTime fechaDelHecho) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fechaDelHecho = fechaDelHecho;
    }

    // Si querés, también podés mantener un constructor vacío para frameworks
    public HechoInputDTO() {}
}
