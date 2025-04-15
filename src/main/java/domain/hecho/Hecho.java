package domain.hecho;

import java.time.LocalDateTime;
import java.util.List;
import domain.coleccion.Categoria;
import domain.hecho.lugar.Lugar;
import domain.hecho.etiqueta.Etiqueta;
import domain.hecho.solicitudes.Solicitud;
import lombok.*;

@Setter
@Getter

public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDateTime fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private Boolean esAnonimo = true;
    private Etiqueta etiqueta;
    private List<Multimedia> multimedia; // puede ser null o vacío

    public Hecho(String titulo, String descripcion, Categoria categoria, LocalDateTime fechaDeAcontecimiento, LocalDateTime fechaDeCarga, Lugar lugar, OrigenDelHecho origen, Boolean esAnonimo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = fechaDeCarga;
        this.lugar = lugar;
        this.origen = origen;
        this.esAnonimo = esAnonimo;
        this.etiqueta = etiqueta;
    }


    public boolean tieneMultimedia() {
        return multimedia != null && !multimedia.isEmpty();
    }

    public void agregarSolicitudDeEliminacion(Solicitud unaSolicitud) {
        this.solicitudesDeEliminacion.add(unaSolicitud);
    }
}
