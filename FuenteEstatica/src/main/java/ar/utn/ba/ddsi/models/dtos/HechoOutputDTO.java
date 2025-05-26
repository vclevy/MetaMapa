package ar.utn.ba.ddsi.models.dtos;


import ar.utn.ba.ddsi.models.entities.hecho.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data

@Getter
@Setter
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private List<Multimedia> multimedia;


    public HechoOutputDTO(String titulo, String descripcion, String categoria, LocalDate fechaDeAcontecimiento, LocalDateTime fechaDeCarga, Lugar lugar, List<Multimedia> multimedia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = fechaDeCarga;
        this.lugar = lugar;
        this.multimedia = multimedia;
    }
}
