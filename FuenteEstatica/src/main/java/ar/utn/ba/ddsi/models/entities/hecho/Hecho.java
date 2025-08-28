package ar.utn.ba.ddsi.models.entities.hecho;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Hecho {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Multimedia> multimedia;

    public Hecho(String titulo, String descripcion, LocalDate fechaDeAcontecimiento, Lugar lugar) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = LocalDateTime.now();
        this.lugar = lugar;
        this.origen = OrigenDelHecho.DATASET;
        this.multimedia = new ArrayList<>();
    }
}
