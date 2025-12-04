package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter@Getter
public class Hecho {
    private String provincia;
    private String categoria;
    private LocalDateTime timestamp;
}