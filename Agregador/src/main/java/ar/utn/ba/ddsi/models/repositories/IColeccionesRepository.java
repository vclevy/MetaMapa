package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;

import java.util.List;

public interface IColeccionesRepository {
    List<Coleccion> findAll();
    Coleccion findByHandle(String handle);
    void save(Coleccion coleccion);
    void delete(Coleccion coleccion);
}
