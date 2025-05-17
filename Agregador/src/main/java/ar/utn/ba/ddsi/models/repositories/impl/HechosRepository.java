package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HechosRepository implements IHechosRepository {
    private List<Hecho> hechos = new ArrayList<>();

    @Override
    public List<Hecho> findAll() {
        return new ArrayList<>(hechos);
    }

    @Override
    public Hecho findById(int id) {
        return null;
    }

    @Override
    public void save(Hecho hecho) {
    }

    @Override
    public void delete(Hecho hecho) {
    }
}
