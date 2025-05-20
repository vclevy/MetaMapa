package ar.utn.ba.ddsi.models.repositories;


import ar.utn.ba.ddsi.models.entities.Coleccion;

public interface IColeccionesRepository {

    Coleccion crearColeccion(String titulo, String descripcion);
}

