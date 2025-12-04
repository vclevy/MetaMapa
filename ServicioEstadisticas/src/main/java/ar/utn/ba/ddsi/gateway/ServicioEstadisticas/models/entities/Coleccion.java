package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter@Setter
@ToString
public class Coleccion {
    private String titulo;
    private List<Hecho> hechos;
}
