package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IColeccionesRepository {
    public List<Coleccion> findAll();
    public Coleccion findByHandle(String handle);
    public void save(Coleccion coleccion);
    public void delete(String handle);
}
