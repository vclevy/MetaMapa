package ar.utn.ba.ddsi.gateway.models.dtos;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;
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
    private String nombreArchivo; // <-- nuevo campo

    public HechoInputDTO(String titulo, String descripcion, String categoria, Lugar lugar, LocalDateTime fechaDelHecho, String nombreArchivo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugar = lugar;
        this.fechaDelHecho = fechaDelHecho;
        this.nombreArchivo = nombreArchivo;
    }
}
