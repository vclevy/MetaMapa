package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.services.fuentes.Fuente;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class FuenteDeHechosRepository implements IFuenteDeHechosRepository {
    private List<Fuente> fuentesDeHechos;

    @Override
    public List<Fuente> findAll() {
        return this.fuentesDeHechos;
    }

    @Override
    public Fuente findById(String handle) {
        return this.fuentesDeHechos.stream()
                .filter(fuente -> fuente.getHandleFuente().equals(handle))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Fuente unaFuenteDeHecho) {
        fuentesDeHechos.add(unaFuenteDeHecho);
    }

    @Override
    public void delete(Fuente unaFuenteDeHechos) {
        this.fuentesDeHechos.remove(unaFuenteDeHechos);
    }

}
