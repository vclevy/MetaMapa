package models.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Hecho {
    private String id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private LocalDate fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Double latitud;
    private Double longitud;
    private String fuente;

    public Hecho(String id, String titulo, String descripcion, String categoria, LocalDate fechaAcontecimiento, LocalDateTime localDateTime, Double latitud, Double longitud, String fuente) {
    }
}
