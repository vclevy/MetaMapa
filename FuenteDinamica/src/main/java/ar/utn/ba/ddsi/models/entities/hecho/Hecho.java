package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.solicitudes.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitudes.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Permisos;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Hecho {
    private Integer id;
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
    private List<String> multimedia;
    private Boolean fueEliminado = false;
    private Usuario contribuyente;
    private Revision revision;

    public Hecho(String titulo, String descripcion, Categoria categoria, LocalDate fechaDeAcontecimiento, LocalDateTime fechaDeCarga, Lugar lugar, OrigenDelHecho origen, Boolean esAnonimo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fechaDeAcontecimiento = fechaDeAcontecimiento;
        this.fechaDeCarga = fechaDeCarga;
        this.lugar = lugar;
        this.origen = origen;
        this.esAnonimo = esAnonimo;
        this.etiquetas = new ArrayList<>();
        this.solicitudesDeEliminacion = new ArrayList<>();
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

    public boolean tieneSolicitudesDeEliminacionAprobada() {
       return solicitudesDeEliminacion.stream().anyMatch(unaSolicitud -> unaSolicitud.getEstado() == EstadoDeSolicitudDeEliminacion.APROBADA);
    }

    public boolean esEditable(){
        return (ChronoUnit.DAYS.between(this.getFechaDeCarga(), LocalDateTime.now())) < 7;
    }

    public boolean esAnonimo(){
        return this.contribuyente == null;
    }
}
