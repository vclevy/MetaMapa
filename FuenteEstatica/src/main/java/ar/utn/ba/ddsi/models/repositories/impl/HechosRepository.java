package ar.utn.ba.ddsi.models.repositories.impl;


import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Primary
public class HechosRepository implements IHechosRepository {

    private List<Hecho> hechosSubidos = new ArrayList<>();

    @Override
    public void agregarHechos(List<Hecho> hechos){
        hechosSubidos.addAll(hechos);
    }

    @Override
    public List<Hecho> findAll() {
        return this.hechosSubidos;
    }

    @Override
    public Hecho findById(int id) {
        return this.hechosSubidos.stream().filter(h->h.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Hecho hecho) {
            hechosSubidos.add(hecho);
    }

    @Override
    public void delete(Hecho hecho) {
        hechosSubidos.removeIf(h -> h.getId() == hecho.getId());
    }

}
