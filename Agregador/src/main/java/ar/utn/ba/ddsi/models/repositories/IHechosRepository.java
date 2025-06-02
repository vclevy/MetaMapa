package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IHechosRepository {
    public List<Hecho> findAll();
    public Hecho findById(Long id);
    public void save(Hecho unHecho);
    public void delete(Hecho unHecho);
}
