package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechosRepository {
    List<ar.utn.ba.ddsi.models.entities.hecho.Hecho> findAll();
    Hecho findById(int id);
    void save(Hecho hecho);
    void delete(Hecho hecho);
}
