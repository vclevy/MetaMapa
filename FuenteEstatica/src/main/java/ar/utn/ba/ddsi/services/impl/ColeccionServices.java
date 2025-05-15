package ar.utn.ba.ddsi.services.impl;


import ar.utn.ba.ddsi.models.entities.Coleccion;
import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.hecho.filtroHecho.FiltroHecho;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;
import ar.utn.ba.ddsi.services.IColeccionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColeccionServices implements IColeccionServices {
    @Autowired
    private IColeccionesRepository repositorioColecciones;

    @Override
    public Coleccion crearColeccion(String titulo, String descripcion) {
        return repositorioColecciones.crearColeccion(titulo, descripcion);
    }

    @Override
    public Coleccion filtrarColeccion(Coleccion coleccion, FiltroHecho filtro) {
        Map<String, Hecho> hechosFiltrados = coleccion.getHechos().entrySet().stream()
                .filter(entry -> filtro.aplica(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        Coleccion nuevaColeccion = crearColeccion(coleccion.getTitulo(), coleccion.getDescripcion());
        nuevaColeccion.setHechos(hechosFiltrados);
        return nuevaColeccion;
    }

    // TODO: trabajar con dtos!!!!


}
