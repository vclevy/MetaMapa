package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class HechosRepository implements IHechosRepository {

    private List<Hecho> hechosSubidos;

    public void agregarHechos(Hecho ... unosHechos){
        Collections.addAll(this.hechosSubidos, unosHechos);
    }

    public List<Hecho> findAll() {
        return this.hechosSubidos;
    }

    public Hecho findById(int id) {
        return this.hechosSubidos.stream().filter(h->h.getId().equals(id)).findFirst().orElse(null);
    }

    public void save(Hecho hecho) {
            hechosSubidos.add(hecho);
    }

    public void delete(Hecho hecho) {
        hechosSubidos.removeIf(h -> h.getId() == hecho.getId());
    }
}
