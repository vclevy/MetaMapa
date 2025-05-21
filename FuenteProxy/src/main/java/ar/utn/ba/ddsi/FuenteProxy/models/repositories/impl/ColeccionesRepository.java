package ar.utn.ba.ddsi.FuenteProxy.models.repositories.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;

import java.util.List;

import ar.utn.ba.ddsi.FuenteProxy.models.repositories.IColeccionesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

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
