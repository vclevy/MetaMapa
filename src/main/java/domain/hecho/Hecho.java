package domain.hecho;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

import domain.coleccion.Categoria;
import domain.hecho.lugar.Lugar;
import domain.hecho.etiqueta.Etiqueta;
import domain.hecho.solicitudes.Solicitud;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private Boolean esAnonimo = true;
    private List<Etiqueta> etiquetas;
    private List<Multimedia> multimedia;

    public Hecho(String titulo, String descripcion, Categoria categoria, LocalDate fechaDeAcontecimiento, LocalDateTime fechaDeCarga, Lugar lugar, OrigenDelHecho origen,Boolean esAnonimo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = fechaDeCarga;
        this.lugar = lugar;
        this.origen = origen;
        this.esAnonimo = esAnonimo;
        this.etiquetas = new ArrayList<>();
    }

    public boolean tieneMultimedia() {
        return multimedia != null && !multimedia.isEmpty();
    }

    public void agregarEtiquetas(Etiqueta... unasEtiquetas) {
        Collections.addAll(this.etiquetas, unasEtiquetas);
    }

    public void agregarSolicitudDeEliminacion(Solicitud unaSolicitud) {
        this.solicitudesDeEliminacion.add(unaSolicitud);
    }
}
