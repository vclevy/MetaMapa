package ar.utn.ba.ddsi.models.entities;


import ar.utn.ba.ddsi.models.coleccion.CriterioDePertenencia;
import ar.utn.ba.ddsi.models.fuentes.Fuente;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private Map<String, Hecho> hechos;
    private List<CriterioDePertenencia> criteriosDePertenencia;
    private List<Fuente> fuentes;

    public Coleccion(String tit, String desc) {
        titulo = tit;
        descripcion = desc;
        hechos = new HashMap<>();
        criteriosDePertenencia = new ArrayList<>();
    }

    public void agregarHecho(Hecho hecho) {
//        if(cumpleCriterios){
//            coleccion.agregarHecho(hecho)
//        }
    }//TODO: A implementar

    public void agregarCriterioDePertenencia(CriterioDePertenencia criterio) {
        criteriosDePertenencia.add(criterio);
    }
}

