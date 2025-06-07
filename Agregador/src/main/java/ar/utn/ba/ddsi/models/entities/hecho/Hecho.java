package ar.utn.ba.ddsi.models.entities.hecho;

import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Hecho {
    private Long idEnFuente;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDate fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Multimedia> multimedia;
    private Usuario usuarioContribuyente;

    private Long idAgregador;
    private LocalDateTime fechaDeCargaDelHecho;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private Boolean esAnonimo = true;
    private List<Etiqueta> etiquetas;
    private Boolean fueEliminado = false;


    public Hecho(Long unIdEnFuente, String unTitulo, String unaDescripcion, Categoria unaCategoria, LocalDate unaFechaDeAcontecimiento, Double unaLatitud, Double unaLongitud, Multimedia unaMultimedia, Usuario unUsuario) {
        // VARIABLES QUE LLEGAN DE INPUT
        this.idEnFuente = unIdEnFuente;
        this.titulo = unTitulo;
        this.descripcion = unaDescripcion;
        this.categoria = unaCategoria;
        this.fechaDeAcontecimiento = unaFechaDeAcontecimiento;
        this.lugar = new Lugar(unaLatitud, unaLongitud);
        // this.multimedia =
        this.usuarioContribuyente = unUsuario;

        // VARIABLES QUE INICIALIZO UNA VEZ QUE SE CREA EL HECHO
        this.fechaDeCargaDelHecho = LocalDateTime.now();
        // ORIGEN LO SETEO SEGUN LA FUENTE
        this.solicitudesDeEliminacion = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
    }
}
