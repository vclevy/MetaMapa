package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Hecho {
    private int id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private LocalDateTime fechaHecho;
    private LocalDateTime createdAt;
    private double latitud;
    private double longitud;

    public Hecho(int id, String titulo, String descripcion, String categoria,
                 LocalDateTime fechaHecho, LocalDateTime createdAt,
                 double latitud, double longitud, Object extra) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaHecho = fechaHecho;
        this.createdAt = createdAt;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
