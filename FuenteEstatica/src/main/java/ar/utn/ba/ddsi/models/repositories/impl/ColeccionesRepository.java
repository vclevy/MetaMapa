package ar.utn.ba.ddsi.models.repositories.impl;


import ar.utn.ba.ddsi.models.entities.Coleccion;
import ar.utn.ba.ddsi.models.repositories.IColeccionesRepository;

import java.util.List;


public class ColeccionesRepository implements IColeccionesRepository {

    private List<Coleccion> colecciones;

    @Override
    public Coleccion crearColeccion(String titulo, String descripcion) {
        Coleccion coleccion = new Coleccion(titulo, descripcion);
        colecciones.add(coleccion);
        return coleccion;
    }

    // TODO implementacion de los DTOS
}
