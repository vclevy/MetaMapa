package ar.utn.ba.ddsi.ServicioEstadisticas.services;

import ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.Coleccion;

import java.util.List;
public interface IColeccionService {
    List<Coleccion> obtenerColecciones();
}
