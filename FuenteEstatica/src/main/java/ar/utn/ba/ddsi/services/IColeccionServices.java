package ar.utn.ba.ddsi.services;


import ar.utn.ba.ddsi.models.entities.Coleccion;
import ar.utn.ba.ddsi.models.hecho.filtroHecho.FiltroHecho;

public interface IColeccionServices
{
    Coleccion crearColeccion(String titulo, String descripcion);

    Coleccion filtrarColeccion(Coleccion coleccion, FiltroHecho filtro);
}
