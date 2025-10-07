package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import ar.utn.ba.ddsi.cliente_liviano.models.entities.Etiqueta;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.Lugar;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.cliente_liviano.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class HechoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
    private LocalDateTime fechaDeAcontecimiento;
    private Lugar lugar;
    private List<Etiqueta> etiquetas;
    private List<String> multimedia;
    private String nombreDeUsuario;
    private String tipoFuente;
}

