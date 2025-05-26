package ar.utn.ba.ddsi.models.entities.hecho;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Hecho {
    private UUID id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Multimedia> multimedia;

    public Hecho(String titulo, String descripcion, String categoria, LocalDate fechaDeAcontecimiento, Lugar lugar) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = LocalDateTime.now();
        this.lugar = lugar;
        this.origen = OrigenDelHecho.DATASET;
        this.multimedia = new ArrayList<>();
    }
}
