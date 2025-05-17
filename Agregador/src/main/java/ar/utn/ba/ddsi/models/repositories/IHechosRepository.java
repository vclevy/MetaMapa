package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IHechosRepository {
    public List<Hecho> findAll();
    public Hecho findById(Integer id);
    public void save(Hecho hecho);
    public void delete(Hecho hecho);
}