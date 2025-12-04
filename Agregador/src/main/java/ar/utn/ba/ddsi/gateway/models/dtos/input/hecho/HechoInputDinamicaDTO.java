package ar.utn.ba.ddsi.gateway.models.dtos.input.hecho;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Etiqueta;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.OrigenDelHecho;
import ar.utn.ba.ddsi.gateway.models.entities.solicitud.Solicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HechoInputDinamicaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private LocalDateTime fechaDeAcontecimiento;
    private LocalDateTime fechaDeCarga;
    private Lugar lugar;
    private OrigenDelHecho origen;
    private List<Solicitud> solicitudesDeEliminacion;
    private List<Etiqueta> etiquetas;
    private Boolean fueEliminado = false;
    private String nombreDeUsuario;
    private Boolean esEditable;
    private List<String> multimedia;
    private Boolean esAnonimo;
}
