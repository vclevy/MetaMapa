package ar.utn.ba.ddsi.gateway.models.dtos.input.hecho;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HechoInputPUTDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private String latitud;
    private String longitud;
    private String fechaAcontecimiento;
    private List<String> multimedia;
}
