package ar.utn.ba.ddsi.models.repositories;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;

import java.util.List;

public interface IFuenteDeHechosRepository {
    public List<IFuenteDeHechos> findAll();
    public IFuenteDeHechos findById(Long id);
    public void save(IFuenteDeHechos unaFuente);
    public void delete(IFuenteDeHechos unaFuente);
    public Long definirId();
}
