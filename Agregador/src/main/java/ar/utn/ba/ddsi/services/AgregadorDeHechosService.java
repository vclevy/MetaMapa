package ar.utn.ba.ddsi.services;

import ar.utn.ba.ddsi.models.entities.coleccion.Coleccion;
import ar.utn.ba.ddsi.services.coleccionService.ColeccionService;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.FuenteDeHechos;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgregadorDeHechosService {
    private final List<FuenteDeHechos> fuentes;

    public AgregadorDeHechosService(List<FuenteDeHechos> fuentes) {
        this.fuentes = fuentes;
    }

    public List<Hecho> obtenerTodosLosHechos() {
        return fuentes.stream()
                .flatMap(f -> f.obtenerHechos().stream())
                .collect(Collectors.toList());
    }

    public List<Hecho> obtenerHechosFiltradosPorColeccion(Coleccion coleccion) {
        return obtenerTodosLosHechos().stream()
                .filter(hecho -> coleccion.cumpleCriterios(hecho,coleccion.getCriterioDePertenencia()))
                .collect(Collectors.toList());
    }
}