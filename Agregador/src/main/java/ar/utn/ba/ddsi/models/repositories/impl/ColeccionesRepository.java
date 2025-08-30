package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ColeccionesRepository implements IColeccionesRepository {
    private List<Coleccion> colecciones;

    public ColeccionesRepository() {
        colecciones = new ArrayList<>();
    }

    @Override
    public List<Coleccion> findAll() {
        return this.colecciones;
    }

    @Override
    public Coleccion findByHandle(String handle) {
        return colecciones.stream().filter(unaColeccion -> unaColeccion.getHandle().equals(handle)).findFirst().orElse(null);
    }

    @Override
    public void save(Coleccion coleccion) {
        int index = -1;
        for (int i = 0; i < colecciones.size(); i++) {
            if (colecciones.get(i).getHandle().equals(coleccion.getHandle())) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            colecciones.set(index, coleccion);
        } else {
            colecciones.add(coleccion);
        }
    }

    @Override
    public void delete(String handle) {
        Coleccion coleccionAEliminar = colecciones.stream()
                .filter(c -> c.getHandle().equals(handle))
                .findFirst()
                .orElse(null);

        if (coleccionAEliminar != null) {
            colecciones.remove(coleccionAEliminar);
        }
    }
}
