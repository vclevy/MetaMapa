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
    private LocalDateTime fechaDeCargaDelHecho;
    private Lugar lugar;
    private List<Multimedia> multimedia;
    private Usuario usuarioContribuyente;
    private List<Etiqueta> etiquetas;
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    private Long idAgregador;
    private OrigenDelHecho origen;
    private Boolean esAnonimo = true;
    private Boolean fueEliminado = false;
}
