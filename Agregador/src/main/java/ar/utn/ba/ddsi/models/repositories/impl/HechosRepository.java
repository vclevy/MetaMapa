package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HechosRepository implements IHechosRepository {
    private List<Hecho> hechos;

    @Override
    public List<Hecho> findAll() {
        return this.hechos;
    }

    @Override
    public Hecho findById(Long id) {
        return this.hechos.stream().filter(unHecho -> unHecho.getIdAgregador().equals(id)).findFirst().orElse(null);
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
