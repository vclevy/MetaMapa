package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IFuenteDeHechosRepository;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class FuenteDeHechosRepository implements IFuenteDeHechosRepository {
    private List<IFuenteDeHechos> fuentesDeHechos;

    @Override
    public List<IFuenteDeHechos> findAll() {
        return this.fuentesDeHechos;
    }

    @Override
    public IFuenteDeHechos findById(Long id) {
        return this.fuentesDeHechos.stream().filter(unaFuenteDeHechos -> unaFuenteDeHechos.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(IFuenteDeHechos unaFuenteDeHecho) {
        unaFuenteDeHecho.setId(this.definirId());
        fuentesDeHechos.add(unaFuenteDeHecho);
    }

    @Override
    public void delete(IFuenteDeHechos unaFuenteDeHechos) {
        this.fuentesDeHechos.remove(unaFuenteDeHechos);
    }

    @Override
    public Long definirId() {
        List<IFuenteDeHechos> fuentesDeHechos = this.findAll();

        Long maxId = fuentesDeHechos
                .stream()
                .map(IFuenteDeHechos::getId)
                .filter(Objects::nonNull)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}
