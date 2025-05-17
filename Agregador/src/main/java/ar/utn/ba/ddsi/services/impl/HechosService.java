package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;

public class HechosService implements IHechosService {
    // @Autowired
    private IHechosRepository hechosRepository;

    /*
    public void agregarHecho(Hecho hecho) {
        hechos.add(hecho);
    }

    public List<Hecho> obtenerTodos() {
        return new ArrayList<>(hechos);
    }
    */

}
