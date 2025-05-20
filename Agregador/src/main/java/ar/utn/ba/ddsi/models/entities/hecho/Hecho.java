package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
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
    private List<Multimedia> multimedia;
    private Boolean fueEliminado = false;
    private Usuario contribuyente;
    private Revision revision;


    public Hecho(
            String titulo,
            String descripcion,
            Categoria categoria,
            double latitud,
            double longitud,
            LocalDate fechaAcontecimiento,
            OrigenDelHecho origen,
            Usuario usuario
            ) {
        this.id = UUID.randomUUID().hashCode();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.lugar.setLatitud(latitud);
        this.lugar.setLongitud(longitud);
        this.fechaDeAcontecimiento = fechaAcontecimiento;
        this.origen = origen;
        this.fechaDeCarga = LocalDateTime.now();
        this.solicitudesDeEliminacion = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
        this.multimedia = new ArrayList<>();
        this.contribuyente = usuario;
    }
}
