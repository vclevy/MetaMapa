package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class Coleccion {
    private String titulo;
    private String descripcion;
    private String handle;
    private List<Hecho> hechos;
    private List<Criterio> criterioDePertenencia;

    public Coleccion (String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
    }
}
