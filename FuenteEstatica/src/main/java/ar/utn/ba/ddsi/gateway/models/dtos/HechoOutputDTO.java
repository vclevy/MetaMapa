package ar.utn.ba.ddsi.gateway.models.dtos;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDateTime fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private List<String> multimedia;
    private String nombreArchivo;

    public HechoOutputDTO(Long id, String titulo, String descripcion, Categoria categoria,
                          LocalDateTime fechaDeAcontecimiento, LocalDateTime fechaDeCarga,
                          Lugar lugar, List<String> multimedia, String nombreArchivo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = fechaDeCarga;
        this.lugar = lugar;
        this.multimedia = multimedia;
        this.nombreArchivo = nombreArchivo;
    }
}
