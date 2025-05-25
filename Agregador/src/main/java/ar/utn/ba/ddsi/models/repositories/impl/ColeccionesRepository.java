package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import lombok.Getter;
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
        this.colecciones.add(coleccion);
    }

    @Override
    public void delete(Coleccion coleccion) {
        this.colecciones.remove(coleccion);
    }
}
