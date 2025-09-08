package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter@Setter
public class Coleccion {
    private String titulo;
    private List<Hecho> hechos;
}
