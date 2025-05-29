package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HechosRepository implements IHechosRepository {
    private List<Hecho> hechos;

    public HechosRepository() {
        this.hechos = new ArrayList<>();
    }

    @Override
    public List<Hecho> findAll() {
        return this.hechos;
    }

    @Override
    public Hecho findById(Integer id) {
        return this.hechos.stream().filter(unHecho -> unHecho.getIdAgregador() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Hecho hecho) {
        this.hechos.add(hecho);
    }

    @Override
    public void delete(Hecho hecho) {
        this.hechos.remove(hecho);
    }
}
