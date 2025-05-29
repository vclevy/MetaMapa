package ar.utn.ba.ddsi.models.entities.coleccion;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private String handle;
    private List<Hecho> hechos;
    private List<FuenteDeHechos> fuentesDeHechos;
    private List<Criterio> criterioDePertenencia;

    public Coleccion (String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.handle = UUID.randomUUID().toString();
    }

    public static boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
        for (Criterio c : criterios) {
            if (!c.cumple(hecho)) return false;
        }
        return true;
    }
}
