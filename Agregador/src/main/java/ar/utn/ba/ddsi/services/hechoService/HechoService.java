package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService {
    @Autowired
    private IHechosRepository hechosRepository;


}
