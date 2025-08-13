package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.Fuente;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;

import java.util.List;

public interface IFuenteDeHechosRepository {
    public List<Fuente> findAll();
    public Fuente findById(String handle);
    public void save(Fuente unaFuente);
    public void delete(Fuente unaFuente);
}
