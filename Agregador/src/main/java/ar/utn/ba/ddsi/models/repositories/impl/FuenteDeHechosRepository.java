package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuenteDeHechosRepository {
    private List<FuenteDeHechos> fuentesDeHechos;

    @Override
    public List<FuenteDeHechos> findAll() {
        return this.fuentesDeHechos;
    }

    @Override
    public FuenteDeHechos findById(Long id) {
        return this.fuentesDeHechos.stream().filter(unaFuenteDeHechos -> unaFuenteDeHechos.getClass().get);
    }

    @Override
    public void save(Hecho unHecho) {
        hechos.add(unHecho);
    }

    @Override
    public void delete(Hecho unHecho) {
        this.hechos.remove(unHecho);
    }
}
