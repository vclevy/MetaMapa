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
        this.hechosRepository.save(unHecho);
    }
}
