package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HechoService {

    private List<Hecho> hechos = new ArrayList<>();

    public void agregarHecho(Hecho hecho) {
        hechos.add(hecho);
    }

    public List<Hecho> obtenerTodos() {
        return new ArrayList<>(hechos);
    }

    public List<Hecho> obtenerHechosPorHandle(String handle) {
        return hechos.stream()
                .filter(h -> h.getHandlesColecciones().contains(handle))
                .collect(Collectors.toList());
    }

}
