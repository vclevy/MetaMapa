package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class HechoService implements IHechoService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void registrarHechoDesdeFuente(Hecho unHecho) {
        unHecho.setIdAgregador(this.definirId());
        this.hechosRepository.save(unHecho);
    }

    @Override
    public Long definirId() {
        List<Hecho> hechosDeRepositorio = this.hechosRepository.findAll();

        Long maxId = hechosDeRepositorio
                .stream()
                .map(Hecho::getIdAgregador)
                .filter(Objects::nonNull)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}
