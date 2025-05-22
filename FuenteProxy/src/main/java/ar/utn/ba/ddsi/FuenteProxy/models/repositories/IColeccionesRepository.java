package ar.utn.ba.ddsi.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;

import java.util.List;

public interface IColeccionesRepository {
    public List<Coleccion> findAll();
    public Coleccion findByHandle(String handle);
    public void save(Coleccion coleccion);
    public void delete(Coleccion coleccion);
}
