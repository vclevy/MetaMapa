package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

import java.util.List;

public interface IFuenteDeHechosRepository {
    public List<Hecho> findAll();
    public Hecho findById(Long id);
    public void save(Hecho unHecho);
    public void delete(Hecho unHecho);
}
