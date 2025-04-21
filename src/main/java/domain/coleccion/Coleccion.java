package domain.coleccion;

import domain.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private Map<String, Hecho> hechos;
    private List<CriterioDePertenencia> criteriosDePertenencia;

    public Coleccion() {
        hechos = new HashMap<>();
        criteriosDePertenencia = new ArrayList<>();
    }

    public void agregarHecho(Hecho hecho) {
        if (criteriosDePertenencia != null && !criteriosDePertenencia.isEmpty()) {
            boolean cumpleTodos = criteriosDePertenencia.stream().allMatch(criterio -> criterio.cumple(hecho));

            if (cumpleTodos) {
                hechos.put(hecho.getTitulo(), hecho);
            }
            } else {
                hechos.put(hecho.getTitulo(), hecho);
            }
    }

    public void agregarCriterioDePertenencia(CriterioDePertenencia criterio) {
        criteriosDePertenencia.add(criterio);
    }

    public void eliminarHechoPorSolicitudDeEliminacionAprobada(Hecho unHecho) {
        if (unHecho.tieneSolicitudesDeEliminacionAprobada()) {
            hechos.remove(unHecho.getTitulo());
        }
    }
}

